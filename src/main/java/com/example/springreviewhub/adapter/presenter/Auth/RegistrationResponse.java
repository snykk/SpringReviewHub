package com.example.springreviewhub.adapter.presenter.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegistrationResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean isActive;
    private boolean emailVerified;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Chaining setter methods
    public RegistrationResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public RegistrationResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegistrationResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegistrationResponse setRole(String role) {
        this.role = role;
        return this;
    }

    public RegistrationResponse setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public RegistrationResponse setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public RegistrationResponse setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RegistrationResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public RegistrationResponse setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public RegistrationResponse setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public RegistrationResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RegistrationResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
