package com.example.springreviewhub.adapter.presenter.User;

import com.example.springreviewhub.core.domain.UserDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String role;

    public UserResponse(UserDomain userDomain) {
        this.id = userDomain.getId();
        this.username = userDomain.getUsername();
        this.email = userDomain.getEmail();
        this.role = userDomain.getRole().name();
    }
}
