package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
public class UserDomain {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role; // Roles: "ROLE_ADMIN", "ROLE_REVIEWER"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private LocalDateTime lastLoginAt;
    private int failedLoginAttempts;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private boolean emailVerified;
    private String bio;
    private LocalDateTime deletedAt;

    public boolean isAdmin() {
        return Role.Admin.equals(this.role);
    }

    // Chaining Setters
    public UserDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDomain setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDomain setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDomain setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDomain setRole(Role role) {
        this.role = role;
        return this;
    }

    public UserDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserDomain setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public UserDomain setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public UserDomain setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    public UserDomain setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserDomain setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserDomain setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserDomain setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public UserDomain setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserDomain setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDomain user = (UserDomain) o;
        return isActive == user.isActive &&
                failedLoginAttempts == user.failedLoginAttempts &&
                emailVerified == user.emailVerified &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt) &&
                Objects.equals(lastLoginAt, user.lastLoginAt) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(address, user.address) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(bio, user.bio) &&
                Objects.equals(deletedAt, user.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, role, createdAt, updatedAt, isActive, lastLoginAt, failedLoginAttempts, phoneNumber, address, dateOfBirth, emailVerified, bio, deletedAt);
    }
}
