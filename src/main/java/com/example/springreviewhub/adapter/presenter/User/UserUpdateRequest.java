    package com.example.springreviewhub.adapter.presenter.user;

    import jakarta.validation.constraints.*;
    import lombok.Getter;
    import lombok.Setter;

    import java.time.LocalDate;

    @Setter
    @Getter
    public class UserUpdateRequest {

        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;

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
