package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.user.UserUpdateRequest;
import com.example.springreviewhub.adapter.presenter.user.UserResponse;
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
     * This method is used to map user input from the API layer into domain-level
     * objects that the application can process. Password encoding and role assignment
     * should be handled elsewhere, if applicable.
     */
    public static UserDomain fromUserUpdateRequestToDomain(UserUpdateRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        return new UserDomain()
                .setUsername(userRequest.getUsername());
    }

    /**
     * Convert a `UserDomain` object to a `UserResponse` object.
     *
     * @param userDomain the domain object containing user data
     * @return a `UserResponse` object to be sent as an API response
     *
     * This method is used to convert domain models into response objects,
     * which can be returned to the client via the API layer.
     */
    public static UserResponse fromDomainToUserResponse(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        return new UserResponse()
                .setId(userDomain.getId())
                .setUsername(userDomain.getUsername())
                .setEmail(userDomain.getEmail())
                .setRole(userDomain.getRole().name())
                .setCreatedAt(userDomain.getCreatedAt())
                .setUpdatedAt(userDomain.getUpdatedAt());
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
    public static List<UserResponse> fromDomainListToResponseList(List<UserDomain> userDomains) {
        if (userDomains == null || userDomains.isEmpty()) {
            return List.of();
        }

        return userDomains.stream()
                .map(UserMapper::fromDomainToUserResponse)
                .collect(Collectors.toList());
    }
}
