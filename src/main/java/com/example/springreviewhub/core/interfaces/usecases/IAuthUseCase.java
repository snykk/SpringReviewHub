package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.UserDomain;

/**
 * Interface for Authentication Use Cases.
 * <p>
 * This interface defines the contract for user authentication and registration use cases.
 * It includes methods for registering new users, authenticating users, and retrieving the authenticated user's details.
 * </p>
 */
public interface IAuthUseCase {

    /**
     * Registers a new user in the system.
     * <p>
     * This method handles the creation of a new user account by taking the user details
     * and saving them to the system. It performs any necessary validations or transformations
     * and returns the saved user object.
     * </p>
     *
     * @param user the user to be registered
     * @return the registered user object
     */
    UserDomain register(UserDomain user);

    /**
     * Authenticates a user and generates an authentication token.
     * <p>
     * This method validates the user's credentials (such as username and password).
     * If the authentication is successful, it generates and returns an authentication token.
     * </p>
     *
     * @param user the user attempting to authenticate
     * @return a token representing the user's authenticated session
     */
    String authenticate(UserDomain user);

    /**
     * Retrieves details of the authenticated user.
     * <p>
     * This method takes the username of the currently authenticated user and returns their details.
     * It ensures that only valid authenticated users' data is retrieved.
     * </p>
     *
     * @param username the username of the authenticated user
     * @return the authenticated user's details as a {@link UserDomain} object
     */
    UserDomain getAuthenticatedUser(String username);

    /**
     * Sends an OTP (One-Time Password) to the user's email address.
     * <p>
     * This method generates and sends an OTP to the specified email address. The OTP is typically
     * used for user verification or password reset processes.
     * </p>
     *
     * @param email the email address to which the OTP will be sent
     */
    void sendOTP(String email);

    /**
     * Verifies the OTP entered by the user.
     * <p>
     * This method checks whether the provided OTP matches the one sent to the user's email address.
     * It is used as part of the authentication or verification process, often during login or account recovery.
     * </p>
     *
     * @param email the email address associated with the OTP
     * @param otp   the OTP entered by the user for verification
     */
    void verifyOTP(String email, String otp);
}
