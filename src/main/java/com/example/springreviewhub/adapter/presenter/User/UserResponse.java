package com.example.springreviewhub.adapter.presenter.user;

import com.example.springreviewhub.core.domain.UserDomain;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String role;

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
}
