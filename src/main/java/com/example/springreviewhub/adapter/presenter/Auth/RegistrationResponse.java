package com.example.springreviewhub.adapter.presenter.Auth;

import com.example.springreviewhub.core.domain.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegistrationResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;



    // Static mapper method
    public static RegistrationResponse fromDomain(UserDomain userDomain) {
        return new RegistrationResponse(
                userDomain.getId(),
                userDomain.getUsername(),
                userDomain.getEmail(),
                userDomain.getRole().name(),
                userDomain.getCreatedAt(),
                userDomain.getUpdatedAt()
        );
    }
}
