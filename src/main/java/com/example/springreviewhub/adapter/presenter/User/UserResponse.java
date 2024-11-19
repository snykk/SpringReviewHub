package com.example.springreviewhub.adapter.presenter.user;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean isActive;
    private LocalDateTime lastLoginAt;
    private int failedLoginAttempts;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private boolean emailVerified;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Chaining setter methods
    public UserResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public UserResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserResponse setRole(String role) {
        this.role = role;
        return this;
    }

    public UserResponse setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public UserResponse setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public UserResponse setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    public UserResponse setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserResponse setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserResponse setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public UserResponse setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserResponse setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
