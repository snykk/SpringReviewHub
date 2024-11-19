package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.UserMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.user.UserChangeEmailRequest;
import com.example.springreviewhub.adapter.presenter.user.UserChangePasswordRequest;
import com.example.springreviewhub.adapter.presenter.user.UserUpdateRequest;
import com.example.springreviewhub.adapter.presenter.user.UserResponse;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.usecases.IUserUseCase;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final IUserUseCase userUseCase;

    @Autowired
    public UserController(IUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(@AuthenticationPrincipal Claims claims) {
        UserDomain authenticatedUser = userUseCase.getAuthenticatedUser(claims.getSubject());

        return ResponseEntity.ok(BaseResponse.success(
                "user data fetched successfully",
                UserMapper.fromDomainToUserResponse(authenticatedUser)));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<UserResponse>>> getAll() {
        List<UserDomain> users = userUseCase.getAllUsers();
        return ResponseEntity.ok(
                BaseResponse.success("all users fetched successfully", UserMapper.fromDomainListToResponseList(users))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getById(@PathVariable Long id) {
        UserDomain user = userUseCase.getUserById(id);

        return ResponseEntity.ok(BaseResponse.success(
                        "user fetched successfully",
                        UserMapper.fromDomainToUserResponse(user))
        );
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<UserResponse>> update(
            @RequestBody @Valid UserUpdateRequest updateRequest,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = ((Number) claims.get("id")).longValue();

        UserDomain userDomain = UserMapper.fromUserUpdateRequestToDomain(updateRequest);

        UserDomain user = userUseCase.updateUser(userId, userDomain);

        return ResponseEntity.ok(
                BaseResponse.success(
                        "user data updated successfully",
                        UserMapper.fromDomainToUserResponse(user))
        );
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponse<String>> delete(@AuthenticationPrincipal Claims claims) {
        Long userId = ((Number) claims.get("id")).longValue();

        userUseCase.deleteUser(userId);

        return ResponseEntity.status(204).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse<String>> changePassword(
            @AuthenticationPrincipal Claims claims,
            @RequestBody @Valid UserChangePasswordRequest changePasswordRequest
    ) {
        Long userId = ((Number) claims.get("id")).longValue();

        userUseCase.changePassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(BaseResponse.success("password changed successfully", null));
    }

    @PostMapping("/change-email")
    public ResponseEntity<BaseResponse<UserResponse>> changeEmail(
            @AuthenticationPrincipal Claims claims,
            @RequestBody @Valid UserChangeEmailRequest userChangeEmailRequest
    ) {
        Long userId = ((Number) claims.get("id")).longValue();

        UserDomain UpdatedUser = userUseCase.changeEmail(userId, userChangeEmailRequest.getNewEmail());

        return ResponseEntity.ok(BaseResponse.success(
                "email changed successfully",
                UserMapper.fromDomainToUserResponse(UpdatedUser)));
    }

}
