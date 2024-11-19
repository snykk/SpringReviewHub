package com.example.springreviewhub.adapter.presenter.user;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserLimitedResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Chaining setter methods
    public UserLimitedResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public UserLimitedResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserLimitedResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserLimitedResponse setRole(String role) {
        this.role = role;
        return this;
    }

    public UserLimitedResponse setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserLimitedResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserLimitedResponse setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserLimitedResponse setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserLimitedResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserLimitedResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
