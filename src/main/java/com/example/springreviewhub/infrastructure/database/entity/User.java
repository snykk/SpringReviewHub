package com.example.springreviewhub.infrastructure.database.entity;

import com.example.springreviewhub.core.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)  // Make sure role is stored as a string representation of the enum
    @Column(nullable = false)
    private Role role; // Use enum Role

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE) // Prevent modification from outside
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column()
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Chaining setters
    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }
}
