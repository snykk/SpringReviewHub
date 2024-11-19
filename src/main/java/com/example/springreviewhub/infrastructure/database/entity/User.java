package com.example.springreviewhub.infrastructure.database.entity;

import com.example.springreviewhub.core.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a User entity in the database.
 * <p>
 * This class maps to the "users" table and contains attributes related to the user,
 * including personal information, account details, and account status.
 * </p>
 */
@Data
@Entity
@Getter
@ToString
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the user, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * The unique username of the user. Cannot be null and must be unique.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * The unique email of the user. Cannot be null and must be unique.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * The password of the user, stored in an encrypted form. Cannot be null.
     */
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * The role assigned to the user, determines access levels. Cannot be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    /**
     * The status of the user's account (active or inactive). Default is true (active).
     */
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isActive;

    /**
     * The timestamp of the last login of the user. This is nullable and can be updated when the user logs in.
     */
    private LocalDateTime lastLoginAt; // Nullable

    /**
     * The number of failed login attempts. Default is 0.
     */
    @Column(nullable = false)
    @ColumnDefault("0")
    private int failedLoginAttempts;

    /**
     * The phone number of the user. This is nullable.
     */
    @Column(unique = true, length = 15)
    private String phoneNumber; // Nullable

    /**
     * The address of the user. This is nullable.
     */
    @Column(length = 255)
    private String address; // Nullable

    /**
     * The user's date of birth. This is nullable.
     */
    private LocalDate dateOfBirth; // Nullable

    /**
     * The verification status of the user's email. Default is false (not verified).
     */
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean emailVerified;

    /**
     * The bio of the user. This is nullable and may contain additional information about the user.
     */
    @Column(length = 500)
    private String bio; // Nullable

    /**
     * The timestamp when the user record was created. This cannot be updated once set.
     */
    @Column(nullable = false, updatable = false)
    // @Setter(AccessLevel.NONE) // Prevent modification from outside
    private LocalDateTime createdAt;

    /**
     * The timestamp when the user record was last updated. This is updated automatically on every change.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * The timestamp when the user record was deleted. This is nullable and indicates when the user account was deleted.
     */
    private LocalDateTime deletedAt; // Nullable

    /**
     * Sets the creation timestamps before the entity is persisted.
     * This method is automatically called before saving the entity for the first time.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Updates the updatedAt timestamp before the entity is updated.
     * This method is automatically called whenever the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID to set
     * @return the current instance of the User object
     */
    public User setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     * @return the current instance of the User object
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     * @return the current instance of the User object
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     * @return the current instance of the User object
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     * @return the current instance of the User object
     */
    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets whether the user account is active.
     *
     * @param isActive true if the account is active, false otherwise
     * @return the current instance of the User object
     */
    public User setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    /**
     * Sets the last login timestamp of the user.
     *
     * @param lastLoginAt the timestamp of the last login
     * @return the current instance of the User object
     */
    public User setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    /**
     * Sets the number of failed login attempts.
     *
     * @param failedLoginAttempts the number of failed attempts to set
     * @return the current instance of the User object
     */
    public User setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        return this;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number to set
     * @return the current instance of the User object
     */
    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets the address of the user.
     *
     * @param address the address to set
     * @return the current instance of the User object
     */
    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dateOfBirth the date of birth to set
     * @return the current instance of the User object
     */
    public User setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Sets whether the user's email is verified.
     *
     * @param emailVerified true if the email is verified, false otherwise
     * @return the current instance of the User object
     */
    public User setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    /**
     * Sets the bio of the user.
     *
     * @param bio the bio to set
     * @return the current instance of the User object
     */
    public User setBio(String bio) {
        this.bio = bio;
        return this;
    }

    /**
     * Sets the creation timestamp of the user.
     *
     * @param createdAt the creation timestamp to set
     * @return the current instance of the User object, allowing for method chaining
     */
    public User setCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        }
        return this;
    }

    /**
     * Sets the last update timestamp of the user.
     *
     * @param updatedAt the last update timestamp to set
     * @return the current instance of the User object, allowing for method chaining
     */
    public User setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        }
        return this;
    }

    /**
     * Sets the deletion timestamp of the user.
     *
     * @param deletedAt the timestamp to set when the user is deleted
     * @return the current instance of the User object
     */
    public User setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
