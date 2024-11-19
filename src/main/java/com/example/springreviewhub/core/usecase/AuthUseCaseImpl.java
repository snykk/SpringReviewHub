package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.exception.*;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.usecases.IAuthUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        Optional<UserDomain> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String authenticate(UserDomain user) {
        UserDomain userDomainFromDB = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!userDomainFromDB.isActive()) {
            throw new AccountLockedException("Account is locked");
        }

        if (!userDomainFromDB.isEmailVerified()) {
            throw new EmailNotVerifiedException("Email is not verified");
        }

        if (!passwordEncoder.matches(user.getPassword(), userDomainFromDB.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getUsername(), userDomainFromDB);
    }

    @Override
    public UserDomain getAuthenticatedUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
