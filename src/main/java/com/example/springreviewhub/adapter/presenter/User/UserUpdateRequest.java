    package com.example.springreviewhub.adapter.presenter.user;

    import jakarta.validation.constraints.*;
    import lombok.Getter;
    import lombok.Setter;

    @Setter
    @Getter
    public class UserUpdateRequest {

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;
    }
