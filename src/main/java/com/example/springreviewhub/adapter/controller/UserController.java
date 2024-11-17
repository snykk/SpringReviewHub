package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.presenter.User.UserResponse;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.IUserUseCase;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserUseCase userUseCase;

    @Autowired
    public UserController(IUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Claims userDetails) {
        UserDomain authenticatedUser = userUseCase.getAuthenticatedUser(userDetails.getSubject());
        return ResponseEntity.ok(new UserResponse(authenticatedUser));
    }
}
