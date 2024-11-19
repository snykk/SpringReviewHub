package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.UserMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.user.UserChangeEmailRequest;
import com.example.springreviewhub.adapter.presenter.user.UserChangePasswordRequest;
import com.example.springreviewhub.adapter.presenter.user.UserUpdateRequest;
import com.example.springreviewhub.adapter.presenter.user.AdvanceUserResponse;
import com.example.springreviewhub.adapter.presenter.user.UserLimitedResponse;
import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.interfaces.usecases.IUserUseCase;
import com.example.springreviewhub.infrastructure.database.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> me(
            @AuthenticationPrincipal Claims claims
    ) {
        UserDomain authenticatedUser = userUseCase.getAuthenticatedUser(claims.getSubject());

        return ResponseEntity.ok(BaseResponse.success(
                "user data fetched successfully",
                UserMapper.fromDomainToAdvanceUserResponse(authenticatedUser)));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<?>>> getAll(
            @AuthenticationPrincipal Claims claims
    ) {
        String role = claims.get("role", String.class);

        List<UserDomain> users = userUseCase.getAllUsers();

        if (Role.Admin.name().equals(role)) {
            List<AdvanceUserResponse> adminResponses = UserMapper.fromDomainListToAdvanceUserResponseList(users);

            return ResponseEntity.ok(BaseResponse.success("all users fetched successfully", adminResponses));
        } else {
            List<UserLimitedResponse> userResponses = UserMapper.fromDomainListToUserLimitedResponseList(users);

            return ResponseEntity.ok(BaseResponse.success("all users fetched successfully", userResponses));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = claims.get("role", String.class);

        UserDomain user = userUseCase.getUserById(id);

        if (Role.Admin.name().equals(role)) {
            return ResponseEntity.ok(BaseResponse.success(
                    "user fetched successfully",
                    UserMapper.fromDomainToAdvanceUserResponse(user))
            );
        } else {
            return ResponseEntity.ok(BaseResponse.success(
                    "user fetched successfully",
                    UserMapper.fromDomainToUserLimitedResponse(user))
            );
        }
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> update(
            @RequestBody @Valid UserUpdateRequest updateRequest,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = ((Number) claims.get("id")).longValue();

        UserDomain userDomain = UserMapper.fromUserUpdateRequestToDomain(updateRequest);

        UserDomain user = userUseCase.updateUser(userId, userDomain);

        return ResponseEntity.ok(BaseResponse.success(
                        "user data updated successfully",
                        UserMapper.fromDomainToAdvanceUserResponse(user))
        );
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal Claims claims
    ) {
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

        userUseCase.changePassword(
                userId,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(BaseResponse.success("password changed successfully"));
    }

    @PostMapping("/change-email")
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> changeEmail(
            @AuthenticationPrincipal Claims claims,
            @RequestBody @Valid UserChangeEmailRequest userChangeEmailRequest
    ) {
        Long userId = ((Number) claims.get("id")).longValue();

        UserDomain UpdatedUser = userUseCase.changeEmail(userId, userChangeEmailRequest.getNewEmail());

        return ResponseEntity.ok(BaseResponse.success(
                "email changed successfully",
                UserMapper.fromDomainToAdvanceUserResponse(UpdatedUser)));
    }
}
