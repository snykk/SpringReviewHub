package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.exception.*;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.services.IMailerService;
import com.example.springreviewhub.core.interfaces.services.IRedisService;
import com.example.springreviewhub.core.interfaces.usecases.IAuthUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthUseCaseImpl implements IAuthUseCase {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtUtil;
    private final IRedisService redisService;
    private final IMailerService mailerService;

    @Autowired
    public AuthUseCaseImpl(
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtTokenProvider,
            IRedisService redisService,
            IMailerService mailerService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtTokenProvider;
        this.redisService = redisService;
        this.mailerService = mailerService;
    }

    @Override
    public UserDomain register(UserDomain user) {
        Optional<UserDomain> existingUser = userRepository.findByUsername(user.getUsername(), false);
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String authenticate(UserDomain user) {
        UserDomain userDomainFromDB = userRepository.findByUsername(user.getUsername(), false)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!userDomainFromDB.isActive()) {
            throw new AccountLockedException("Account is locked");
        }

        if (!userDomainFromDB.isEmailVerified()) {
            throw new EmailNotVerifiedException("Email is not verified");
        }

        if (!passwordEncoder.matches(user.getPassword(), userDomainFromDB.getPassword())) {
            int failedAttempts = userDomainFromDB.getFailedLoginAttempts() + 1;
            userDomainFromDB.setFailedLoginAttempts(failedAttempts);

            int remainingAttempts = 3 - failedAttempts;

            if (failedAttempts >= 3) {
                userDomainFromDB.setIsActive(false);
                userRepository.save(userDomainFromDB);
                throw new AccountLockedException("Account locked due to multiple failed login attempts");
            }

            userRepository.save(userDomainFromDB);

            throw new InvalidCredentialsException("Invalid username or password. Remaining attempts: " + remainingAttempts);
        }

        userDomainFromDB.setFailedLoginAttempts(0);

        userDomainFromDB.setLastLoginAt(LocalDateTime.now());

        userRepository.save(userDomainFromDB);

        return jwtUtil.generateToken(user.getUsername(), userDomainFromDB);
    }

    @Override
    public UserDomain getAuthenticatedUser(String username) {
        return userRepository.findByUsername(username, false)
                .orElseThrow(() -> new NotFoundException(String.format("User with 'Username' %s not found.", username)));
    }

    @Override
    public void sendOTP(String email) {
        UserDomain user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with 'Email' %s not found.", email)));

        if (user.isEmailVerified()) {
            throw new EmailAlreadyVerifiedException(String.format("Email %s is already verified.", email));
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        redisService.set(String.format("otp-code:%s", email), otp, 5, TimeUnit.MINUTES);

        String subject = "Verification Email";
        String message = "Your OTP code is: " + otp + "\nIt will expire in 5 minutes.";
        mailerService.sendEmail(email, subject, message);
    }

    @Override
    public void verifyOTP(String email, String otp) {
        UserDomain user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with 'Email' %s not found.", email)));

        if (user.isEmailVerified()) {
            throw new EmailAlreadyVerifiedException(String.format("Email %s is already verified.", email));
        }

        String storedOTP = redisService.get(String.format("otp-code:%s", email));

        if (storedOTP == null) {
            throw new OTPIssueException("OTP has expired or does not exist.");
        }

        if (!storedOTP.equals(otp)) {
            throw new OTPIssueException("Invalid OTP.");
        }

        user.setEmailVerified(true);
        userRepository.save(user);

        redisService.del(String.format("otp-code:%s", email));
    }
}
