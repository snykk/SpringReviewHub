package com.example.springreviewhub.adapter.presenter.Auth;

import com.example.springreviewhub.core.domain.UserDomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    public UserDomain toDomain() {
        return new UserDomain(
                null,
                username,
                null,
                password,
                null,
                null,
                null
        );
    }
}
