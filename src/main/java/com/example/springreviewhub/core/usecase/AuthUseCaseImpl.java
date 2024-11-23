package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.exception.*;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.usecases.IAuthUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthUseCaseImpl implements IAuthUseCase {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtUtil;

    @Autowired
    public AuthUseCaseImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtTokenProvider;
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
}
