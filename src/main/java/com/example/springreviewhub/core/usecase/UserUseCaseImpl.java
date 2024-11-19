package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.exception.BadRequestException;
import com.example.springreviewhub.core.exception.ConflictException;
import com.example.springreviewhub.core.exception.InvalidOldPasswordException;
import com.example.springreviewhub.core.exception.UserNotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.usecases.IUserUseCase;
import com.example.springreviewhub.core.util.UpdateUtils;
import com.example.springreviewhub.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("user not found"));
    }


    @Override
    public List<UserDomain> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDomain getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    @Override
    public UserDomain updateUser(Long userId, UserDomain updatedUser) {
        UserDomain existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if (updatedUser.getUsername() != null) {
            if (userRepository.findByUsername(updatedUser.getUsername())
                    .filter(user -> !user.getId().equals(existingUser.getId()))
                    .isPresent()) {
                throw new ConflictException("username already exists");
            }
            existingUser.setUsername(updatedUser.getUsername());
        }

        UpdateUtils.updateIfNotNull(existingUser::setPhoneNumber, updatedUser.getPhoneNumber());
        UpdateUtils.updateIfNotNull(existingUser::setAddress, updatedUser.getAddress());
        UpdateUtils.updateIfNotNull(existingUser::setDateOfBirth, updatedUser.getDateOfBirth());
        UpdateUtils.updateIfNotNull(existingUser::setBio, updatedUser.getBio());

        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUser(Long userId) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        userRepository.delete(user.getId());
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new BadRequestException("old password cannot be the same as the new password");
        }

        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException("old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public UserDomain changeEmail(Long userId, String newEmail) {
        UserDomain existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if (existingUser.getEmail().equals(newEmail)) {
            throw new ConflictException("email did not change");
        }

        if (userRepository.findByEmail(newEmail)
                .filter(user -> !user.getId().equals(existingUser.getId()))
                .isPresent()) {
            throw new ConflictException("email already exists");
        }

        existingUser.setEmail(newEmail).
                setEmailVerified(false);

        return userRepository.save(existingUser);
    }
}
