package com.example.springreviewhub.adapter.presenter.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public UserResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
