package com.example.springreviewhub.adapter.presenter.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserChangePasswordRequest {
    @NotBlank(message = "Old password is required")
    @Size(min = 8, message = "Old password must be at least 8 characters")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New Password must be at least 8 characters")
    private String newPassword;
}
