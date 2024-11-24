package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.AuthMapper;
import com.example.springreviewhub.adapter.presenter.auth.*;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.services.IMailerService;
import com.example.springreviewhub.core.interfaces.services.IRedisService;
import com.example.springreviewhub.core.interfaces.usecases.IAuthUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final IAuthUseCase authUseCase;


    @Autowired
    public AuthController(IAuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegistrationResponse>> register(
            @RequestBody @Valid RegistrationRequest registrationRequest
    ) {
        UserDomain userDomain = AuthMapper.fromRegisRequestToUserDomain(registrationRequest);

        UserDomain registeredUser = authUseCase.register(userDomain);

        RegistrationResponse response = AuthMapper.fromUserDomainToRegisResponse(registeredUser);

        return ResponseEntity.ok(BaseResponse.success("user created successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        UserDomain userDomain = AuthMapper.fromLoginRequestToUserDomain(loginRequest);

        String token = authUseCase.authenticate(userDomain);

        return ResponseEntity.ok(BaseResponse.success("login success", new LoginResponse(token)));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<BaseResponse<Void>> sendOTP(
            @RequestBody @Valid SendOTPRequest sendOTPRequest
    ) {
        String email = sendOTPRequest.getEmail();

        authUseCase.sendOTP(email);

        return ResponseEntity.ok(BaseResponse.success(String.format("OTP code will be sent to %s", email), null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse<Void>> verifyOTP(
            @RequestBody @Valid VerifyOTPRequest verifyOTPRequest
    ) {
        String email = verifyOTPRequest.getEmail();
        String otp = verifyOTPRequest.getOtp();

        authUseCase.verifyOTP(email, otp);

        return ResponseEntity.ok(BaseResponse.success("OTP verified successfully", null));
    }

}
