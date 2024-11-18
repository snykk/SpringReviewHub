package com.example.springreviewhub.adapter.presenter.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistrationResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
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

    public RegistrationResponse setCreatedAt(LocalDateTime createAt) {
        this.createdAt = createAt;
        return this;
    }

    public RegistrationResponse setUpdatedAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
        return this;
    }
}
