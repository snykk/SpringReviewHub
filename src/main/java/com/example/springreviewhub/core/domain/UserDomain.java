package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDomain user = (UserDomain) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, role);
    }
}
