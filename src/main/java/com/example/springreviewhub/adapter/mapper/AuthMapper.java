package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.auth.LoginRequest;
import com.example.springreviewhub.adapter.presenter.auth.RegistrationRequest;
import com.example.springreviewhub.adapter.presenter.auth.RegistrationResponse;
import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.core.domain.UserDomain;

public class AuthMapper {

    /**
     * Convert `LoginRequest` to `UserDomain`.
     *
     * @param loginRequest the login request containing username and password
     * @return a `UserDomain` object with only username and password set
     *
     * This method is typically used for authentication processes, where only
     * minimal user data (username and password) is needed.
     */
    public static UserDomain fromLoginRequestToUserDomain(LoginRequest loginRequest) {
        return new UserDomain()
                .setUsername(loginRequest.getUsername())
                .setPassword(loginRequest.getPassword());
    }

    /**
     * Convert `RegistrationRequest` to `UserDomain`.
     *
     * @param registrationRequest the registration request containing user details
     * @return a `UserDomain` object populated with registration data
     *
     * This method includes default role assignment as `Role.Reviewer`, which
     * can be updated later based on business rules.
     */
    public static UserDomain fromRegisRequestToUserDomain(RegistrationRequest registrationRequest) {
        return new UserDomain()
                .setUsername(registrationRequest.getUsername())
                .setEmail(registrationRequest.getEmail())
                .setPassword(registrationRequest.getPassword())
                .setRole(Role.Reviewer); // Default to Reviewer
    }

    /**
     * Convert `UserDomain` to `RegistrationResponse`.
     *
     * @param userDomain the user domain object to be converted
     * @return a `RegistrationResponse` object containing user details for response
     *
     * This method is used to provide structured response data after a successful
     * registration or user creation process.
     */
    public static RegistrationResponse fromUserDomainToRegisResponse(UserDomain userDomain) {
        return new RegistrationResponse(
                userDomain.getId(),
                userDomain.getUsername(),
                userDomain.getEmail(),
                userDomain.getRole().name(),
                userDomain.getCreatedAt(),
                userDomain.getUpdatedAt()
        );
    }
}
