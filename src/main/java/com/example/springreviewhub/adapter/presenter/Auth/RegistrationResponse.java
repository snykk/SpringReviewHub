package com.example.springreviewhub.adapter.presenter.Auth;

import com.example.springreviewhub.core.domain.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String password;

    // Static mapper method
    public static RegistrationResponse fromDomain(UserDomain userDomain) {
        return new RegistrationResponse(
                userDomain.getId(),
                userDomain.getUsername(),
                userDomain.getEmail(),
                userDomain.getRole(),
                userDomain.getPassword()
        );
    }
}
