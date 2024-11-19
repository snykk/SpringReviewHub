package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.UserDomain;

import java.util.List;

/**
 * Interface for User Use Cases.
 * <p>
 * This interface defines the operations related to user management within the application.
 * It includes methods for retrieving, updating, and managing user accounts, as well as handling
 * user-specific actions like password and email updates.
 * </p>
 */
public interface IUserUseCase {

    /**
     * Retrieves the authenticated user's details.
     * <p>
     * This method fetches the details of a user based on their username. It is typically used
     * after a user has successfully logged in to retrieve their profile information.
     * </p>
     *
     * @param username the username of the authenticated user
     * @return the {@link UserDomain} object representing the authenticated user
     */
    UserDomain getAuthenticatedUser(String username);

    /**
     * Retrieves a list of all users.
     * <p>
     * This method fetches all users in the system. It is primarily used for administrative
     * purposes or to display a list of registered users.
     * </p>
     *
     * @return a list of {@link UserDomain} objects representing all users
     */
    List<UserDomain> getAllUsers();

    /**
     * Retrieves a user's details by their unique ID.
     * <p>
     * This method fetches the details of a user based on their ID. It is commonly used
     * for profile viewing or administrative actions.
     * </p>
     *
     * @param id the unique identifier of the user
     * @return the {@link UserDomain} object representing the user
     */
    UserDomain getUserById(Long id);

    /**
     * Updates a user's details.
     * <p>
     * This method allows the modification of a user's profile information. It ensures
     * that changes are properly validated and applied to the user's account.
     * </p>
     *
     * @param userId      the unique identifier of the user to be updated
     * @param updatedUser the updated user details
     * @return the {@link UserDomain} object representing the updated user
     */
    UserDomain updateUser(Long userId, UserDomain updatedUser);

    /**
     * Deletes a user account by their unique ID.
     * <p>
     * This method removes a user's account from the system. It is typically used for
     * account termination or administrative cleanup.
     * </p>
     *
     * @param userId the unique identifier of the user to be deleted
     */
    void deleteUser(Long userId);

    /**
     * Changes a user's password.
     * <p>
     * This method allows a user to update their password. It validates the old password
     * before applying the new password to ensure security.
     * </p>
     *
     * @param userId      the unique identifier of the user changing their password
     * @param oldPassword the current password of the user
     * @param newPassword the new password to be set
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Changes a user's email address.
     * <p>
     * This method allows a user to update their email address. It validates the new email
     * for proper formatting and ensures it does not conflict with existing accounts.
     * </p>
     *
     * @param userId  the unique identifier of the user changing their email
     * @param newEmail the new email address to be set
     * @return the {@link UserDomain} object representing the user with the updated email
     */
    UserDomain changeEmail(Long userId, String newEmail);
}
