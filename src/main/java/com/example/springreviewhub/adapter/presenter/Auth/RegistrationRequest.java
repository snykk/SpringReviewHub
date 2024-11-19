package com.example.springreviewhub.adapter.presenter.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegistrationRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@, $, !, %, *, ?, &)"
    )
    private String password;

    // Optional phone number with regex validation if provided
    @Pattern(
            regexp = "^\\+?\\d{10,15}$",
            message = "Phone number must be valid and can include country code"
    )
    @Size(min = 9, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phoneNumber;

    // Optional address, no validation needed
    private String address;

    // Optional date of birth with past date validation if provided
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    // Optional bio with length restriction
    @Size(max = 255, message = "Bio must not exceed 255 characters")
    private String bio;
}
