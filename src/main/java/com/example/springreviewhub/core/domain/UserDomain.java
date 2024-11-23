package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system.
 * <p>
 * This class serves as the core domain model for user management,
 * including roles, login attempts, and various personal details.
 * It features fluent setter methods for concise updates.
 * </p>
 */
@Getter
@ToString
public class UserDomain {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
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

    private List<ReviewDomain> reviews;

    /**
     * Checks if the user has admin privileges.
     *
     * @return true if the user's role is "Admin", false otherwise.
     */
    public boolean isAdmin() {
        return Role.Admin.equals(this.role);
    }

    /**
     * Checks if the user account is locked due to excessive login failures.
     *
     * @param maxAttempts the maximum allowed failed login attempts.
     * @return true if the account is locked, false otherwise.
     */
    public boolean isLocked(int maxAttempts) {
        return this.failedLoginAttempts >= maxAttempts;
    }

    /**
     * Resets the failed login attempts to zero.
     * This method is used to unlock a user's account after resolving login issues.
     *
     * @return the current instance for method chaining.
     */
    public UserDomain resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
        return this;
    }

    /**
     * Updates the last login timestamp.
     * This method is typically invoked after a successful login.
     *
     * @param lastLoginAt the new login timestamp.
     * @return the current instance for method chaining.
     */
    public UserDomain updateLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    // Fluent Setter Methods for Field Updates

    /**
     * Sets the user ID.
     *
     * @param id the unique identifier for the user.
     * @return the current instance for method chaining.
     */
    public UserDomain setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the username.
     *
     * @param username the user's chosen username.
     * @return the current instance for method chaining.
     */
    public UserDomain setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the email address.
     *
     * @param email the user's email address.
     * @return the current instance for method chaining.
     */
    public UserDomain setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the password.
     *
     * @param password the user's hashed password.
     * @return the current instance for method chaining.
     */
    public UserDomain setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the user's role.
     *
     * @param role the user's role (e.g., Admin, Reviewer).
     * @return the current instance for method chaining.
     */
    public UserDomain setRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt the timestamp when the user was created.
     * @return the current instance for method chaining.
     */
    public UserDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Sets the last update timestamp.
     *
     * @param updatedAt the timestamp of the last user update.
     * @return the current instance for method chaining.
     */
    public UserDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Sets the account's active status.
     *
     * @param isActive true if the account is active, false otherwise.
     * @return the current instance for method chaining.
     */
    public UserDomain setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    /**
     * Sets the last login timestamp.
     *
     * @param lastLoginAt the timestamp of the last login.
     * @return the current instance for method chaining.
     */
    public UserDomain setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    /**
     * Sets the failed login attempts count.
     *
     * @param failedLoginAttempts the number of failed login attempts.
     * @return the current instance for method chaining.
     */
    public UserDomain setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the user's phone number.
     * @return the current instance for method chaining.
     */
    public UserDomain setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets the address.
     *
     * @param address the user's address.
     * @return the current instance for method chaining.
     */
    public UserDomain setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the user's date of birth.
     * @return the current instance for method chaining.
     */
    public UserDomain setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Sets the email verification status.
     *
     * @param emailVerified true if the email is verified, false otherwise.
     * @return the current instance for method chaining.
     */
    public UserDomain setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    /**
     * Sets the bio.
     *
     * @param bio the user's biography or description.
     * @return the current instance for method chaining.
     */
    public UserDomain setBio(String bio) {
        this.bio = bio;
        return this;
    }

    /**
     * Sets the deleted timestamp.
     *
     * @param deletedAt the timestamp of account deletion.
     * @return the current instance for method chaining.
     */
    public UserDomain setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
    /**
     * Sets the list of reviews for the user.
     *
     * @param reviews the list of {@link ReviewDomain} objects representing the reviews for the user
     * @return the current instance of {@link UserDomain} for method chaining
     */
    public UserDomain setReviews(List<ReviewDomain> reviews) {
        this.reviews = reviews;
        return this;
    }
}
