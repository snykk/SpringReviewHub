package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.presenter.Auth.LoginRequest;
import com.example.springreviewhub.adapter.presenter.Auth.LoginResponse;
import com.example.springreviewhub.adapter.presenter.Auth.RegistrationRequest;
import com.example.springreviewhub.adapter.presenter.Auth.RegistrationResponse;
import com.example.springreviewhub.adapter.presenter.User.UserResponse;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.IAuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthUseCase authUsecase;

    @Autowired
    public AuthController(IAuthUseCase userUseCase) {
        this.authUsecase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        UserDomain userDomain = registrationRequest.toDomain();

        UserDomain registeredUser = authUsecase.register(userDomain);

        RegistrationResponse response = RegistrationResponse.fromDomain(registeredUser);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDomain userDomain = loginRequest.toDomain();

        String token = authUsecase.authenticate(userDomain);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        UserDomain authenticatedUser = authUsecase.getAuthenticatedUser(userDetails.getUsername());
        return ResponseEntity.ok(new UserResponse(authenticatedUser));
    }
}
