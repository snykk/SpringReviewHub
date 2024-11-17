package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.presenter.Auth.LoginRequest;
import com.example.springreviewhub.adapter.presenter.Auth.LoginResponse;
import com.example.springreviewhub.adapter.presenter.Auth.RegistrationRequest;
import com.example.springreviewhub.adapter.presenter.Auth.RegistrationResponse;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.IAuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthUseCase authUseCase;

    @Autowired
    public AuthController(IAuthUseCase userUseCase) {
        this.authUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegistrationResponse>> register(@RequestBody RegistrationRequest registrationRequest) {
        UserDomain userDomain = registrationRequest.toDomain();

        UserDomain registeredUser = authUseCase.register(userDomain);

        RegistrationResponse response = RegistrationResponse.fromDomain(registeredUser);

        return ResponseEntity.ok(BaseResponse.success("user created successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        UserDomain userDomain = loginRequest.toDomain();

        String token = authUseCase.authenticate(userDomain);

        return ResponseEntity.ok(BaseResponse.success("login success", new LoginResponse(token)));
    }
}
