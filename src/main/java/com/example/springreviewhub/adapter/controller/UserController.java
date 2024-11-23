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
import com.example.springreviewhub.core.exception.PermissionIssueException;
import com.example.springreviewhub.core.interfaces.usecases.IUserUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
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
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> me(
            @AuthenticationPrincipal Claims claims,
            @RequestParam(defaultValue = "false") boolean includeReviews
    ) {
        UserDomain authenticatedUser = userUseCase.getAuthenticatedUser(claims.getSubject(), includeReviews);

        return ResponseEntity.ok(BaseResponse.success(
                "user data fetched successfully",
                UserMapper.fromDomainToAdvanceUserResponse(authenticatedUser, includeReviews)));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<?>>> getAll(
            @AuthenticationPrincipal Claims claims,
            @RequestParam(defaultValue = "false") boolean includeReviews
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        List<UserDomain> users = userUseCase.getAllUsersWithRole(role, includeReviews);

        if (Role.Admin.name().equals(role)) {
            List<AdvanceUserResponse> adminResponses = UserMapper.fromDomainListToAdvanceUserResponseList(users, includeReviews);

            return ResponseEntity.ok(BaseResponse.success("all users fetched successfully", adminResponses));
        } else {
            List<UserLimitedResponse> userResponses = UserMapper.fromDomainListToUserLimitedResponseList(users, includeReviews);

            return ResponseEntity.ok(BaseResponse.success("all users fetched successfully", userResponses));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims,
            @RequestParam(defaultValue = "false") boolean includeReviews
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        UserDomain user = userUseCase.getUserByIdWithRole(id, role);

        if (Role.Admin.name().equals(role)) {
            return ResponseEntity.ok(BaseResponse.success(
                    "user fetched successfully",
                    UserMapper.fromDomainToAdvanceUserResponse(user, includeReviews))
            );
        } else {
            return ResponseEntity.ok(BaseResponse.success(
                    "user fetched successfully",
                    UserMapper.fromDomainToUserLimitedResponse(user, includeReviews))
            );
        }
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> update(
            @RequestBody @Valid UserUpdateRequest updateRequest,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        UserDomain userDomain = UserMapper.fromUserUpdateRequestToDomain(updateRequest);

        UserDomain user = userUseCase.updateUser(userId, userDomain);

        return ResponseEntity.ok(BaseResponse.success(
                        "user data updated successfully",
                        UserMapper.fromDomainToAdvanceUserResponse(user, false))
        );
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        userUseCase.deleteUser(userId);

        return ResponseEntity.status(204).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse<String>> changePassword(
            @AuthenticationPrincipal Claims claims,
            @RequestBody @Valid UserChangePasswordRequest changePasswordRequest
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        userUseCase.changePassword(
                userId,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(BaseResponse.success("password changed successfully"));
    }

    @PostMapping("/change-email")
    public ResponseEntity<BaseResponse<AdvanceUserResponse>> changeEmail(
            @AuthenticationPrincipal Claims claims,
            @RequestBody @Valid UserChangeEmailRequest userChangeEmailRequest,
            @RequestParam(defaultValue = "false") boolean includeReviews
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        UserDomain UpdatedUser = userUseCase.changeEmail(userId, userChangeEmailRequest.getNewEmail());

        return ResponseEntity.ok(BaseResponse.success(
                "email changed successfully",
                UserMapper.fromDomainToAdvanceUserResponse(UpdatedUser, includeReviews)));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<BaseResponse<String>> activateUser(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        if (!Role.Admin.name().equals(role)) {
            throw new PermissionIssueException("Only admins can activate users");
        }

        userUseCase.activateUser(id);

        return ResponseEntity.ok(BaseResponse.success("User activated successfully"));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<BaseResponse<String>> deactivateUser(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        if (!Role.Admin.name().equals(role)) {
            throw new PermissionIssueException("Only admins can deactivate users");
        }

        userUseCase.deactivateUser(id);

        return ResponseEntity.ok(BaseResponse.success("User deactivated successfully"));
    }

}
