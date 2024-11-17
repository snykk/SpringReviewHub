package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.usecases.IUserUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserUseCaseImpl implements IUserUseCase {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtUtil;

    @Autowired
    public UserUseCaseImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtTokenProvider;
    }

    @Override
    public UserDomain getAuthenticatedUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
