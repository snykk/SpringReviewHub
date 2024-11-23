package com.example.springreviewhub.adapter.presenter.user;

import com.example.springreviewhub.adapter.presenter.review.ReviewResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AdvanceUserResponse {

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReviewResponse> reviews;

    // Chaining setter methods
    public AdvanceUserResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public AdvanceUserResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public AdvanceUserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public AdvanceUserResponse setRole(String role) {
        this.role = role;
        return this;
    }

    public AdvanceUserResponse setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public AdvanceUserResponse setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public AdvanceUserResponse setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    public AdvanceUserResponse setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public AdvanceUserResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public AdvanceUserResponse setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public AdvanceUserResponse setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public AdvanceUserResponse setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public AdvanceUserResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AdvanceUserResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public AdvanceUserResponse setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public AdvanceUserResponse setReviews(List<ReviewResponse> reviews) {
        this.reviews = reviews;
        return this;
    }
}
