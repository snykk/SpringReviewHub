package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.user.UserLimitedResponse;
import com.example.springreviewhub.adapter.presenter.user.UserUpdateRequest;
import com.example.springreviewhub.adapter.presenter.user.AdvanceUserResponse;
import com.example.springreviewhub.core.domain.UserDomain;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {


    /**
     * Convert a `UserRequest` object to a `UserDomain` object.
     *
     * @param userRequest the request object containing user data
     * @return a `UserDomain` object to be used in the core application logic
     *
     * This method is used to map user input from the Controller layer into domain-level
     * objects that the application can process. Password encoding and role assignment
     * should be handled elsewhere, if applicable.
     */
    public static UserDomain fromUserUpdateRequestToDomain(UserUpdateRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        return new UserDomain()
                .setUsername(userRequest.getUsername())
                .setPhoneNumber(userRequest.getPhoneNumber())
                .setAddress(userRequest.getAddress())
                .setDateOfBirth(userRequest.getDateOfBirth())
                .setBio(userRequest.getBio());
    }

    /**
     * Convert a `UserDomain` object to a `UserResponse` object.
     *
     * @param userDomain the domain object containing user data
     * @return a `UserResponse` object to be sent as an API response
     *
     * This method is used to convert domain models into response objects,
     * which can be returned to the client via the Controller layer.
     */
    public static AdvanceUserResponse fromDomainToAdvanceUserResponse(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        return new AdvanceUserResponse()
                .setId(userDomain.getId())
                .setUsername(userDomain.getUsername())
                .setEmail(userDomain.getEmail())
                .setRole(userDomain.getRole().name())
                .setIsActive(userDomain.isActive())
                .setLastLoginAt(userDomain.getLastLoginAt())
                .setFailedLoginAttempts(userDomain.getFailedLoginAttempts())
                .setPhoneNumber(userDomain.getPhoneNumber())
                .setAddress(userDomain.getAddress())
                .setDateOfBirth(userDomain.getDateOfBirth())
                .setEmailVerified(userDomain.isEmailVerified())
                .setBio(userDomain.getBio())
                .setCreatedAt(userDomain.getCreatedAt())
                .setUpdatedAt(userDomain.getUpdatedAt())
                .setDeletedAt(userDomain.getDeletedAt());
    }

    /**
     * Convert a list of `UserDomain` objects to a list of `UserResponse` objects.
     *
     * @param userDomains a list of domain-level user models
     * @return a list of `UserResponse` objects to be sent as an API response
     *
     * This method is useful for batch operations, such as returning a list of users
     * in a paginated API response. It leverages streams for efficient mapping.
     */
    public static List<AdvanceUserResponse> fromDomainListToAdvanceUserResponseList(List<UserDomain> userDomains) {
        if (userDomains == null || userDomains.isEmpty()) {
            return List.of();
        }

        return userDomains.stream()
                .map(UserMapper::fromDomainToAdvanceUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convert a `UserDomain` object to a `UserLimitedResponse` object.
     *
     * @param userDomain the domain object containing user data
     * @return a `UserLimitedResponse` object to be sent as an API response
     *
     * This method is used to convert domain models into response objects,
     * which can be returned to the client via the Controller layer.
     */
    public static UserLimitedResponse fromDomainToUserLimitedResponse(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        return new UserLimitedResponse()
                .setId(userDomain.getId())
                .setUsername(userDomain.getUsername())
                .setEmail(userDomain.getEmail())
                .setRole(userDomain.getRole().name())
                .setPhoneNumber(userDomain.getPhoneNumber())
                .setAddress(userDomain.getAddress())
                .setDateOfBirth(userDomain.getDateOfBirth())
                .setBio(userDomain.getBio())
                .setCreatedAt(userDomain.getCreatedAt())
                .setUpdatedAt(userDomain.getUpdatedAt());
    }

    /**
     * Convert a list of `UserDomain` objects to a list of `UserLimitedResponse` objects.
     *
     * @param userDomains a list of domain-level user models
     * @return a list of `UserLimitedResponse` objects to be sent as an API response
     *
     * This method is useful for batch operations, such as returning a list of users
     * in a paginated API response. It leverages streams for efficient mapping.
     */
    public static List<UserLimitedResponse> fromDomainListToUserLimitedResponseList(List<UserDomain> userDomains) {
        if (userDomains == null || userDomains.isEmpty()) {
            return List.of();
        }

        return userDomains.stream()
                .map(UserMapper::fromDomainToUserLimitedResponse)
                .collect(Collectors.toList());
    }
}
