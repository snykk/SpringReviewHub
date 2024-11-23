package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.Role;
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
    public UserDomain getAuthenticatedUser(String username, boolean includeReviews) {
        return userRepository.findByUsername(username, includeReviews)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDomain> getAllUsersWithRole(String role, boolean includeReviews) {
        return userRepository.findAllWithRole(role, includeReviews);
    }

    @Override
    public UserDomain getUserByIdWithRole(Long id, String role) {
        return userRepository.findByIdWithRole(id, role)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDomain updateUser(Long userId, UserDomain updatedUser) {
        UserDomain existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (updatedUser.getUsername() != null) {
            if (userRepository.findByUsername(updatedUser.getUsername(), false)
                    .filter(user -> !user.getId().equals(existingUser.getId()))
                    .isPresent()) {
                throw new ConflictException("Username already exists");
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
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.softDelete(userId);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new BadRequestException("Old password cannot be the same as the new password");
        }

        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public UserDomain changeEmail(Long userId, String newEmail) {
        UserDomain existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (existingUser.getEmail().equals(newEmail)) {
            throw new ConflictException("Email did not change");
        }

        if (userRepository.findByEmail(newEmail)
                .filter(user -> !user.getId().equals(existingUser.getId()))
                .isPresent()) {
            throw new ConflictException("Email already exists");
        }

        existingUser.setEmail(newEmail).setEmailVerified(false);

        return userRepository.save(existingUser);
    }

    @Override
    public void activateUser(Long userId) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isActive()) {
            throw new ConflictException("User is already active");
        }

        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long userId) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new ConflictException("User is already deactivated");
        }

        user.setIsActive(false);
        userRepository.save(user);
    }

}

