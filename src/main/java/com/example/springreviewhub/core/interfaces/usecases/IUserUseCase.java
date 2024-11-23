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
     * This method fetches the details of the authenticated user based on their username.
     * It can be used to retrieve user profile information, including their associated reviews
     * if requested.
     * </p>
     *
     * @param username the username of the authenticated user
     * @param includeReviews flag to indicate whether to include reviews in the user details
     * @return the {@link UserDomain} object representing the authenticated user
     */
    UserDomain getAuthenticatedUser(String username, boolean includeReviews);

    /**
     * Retrieves all users with a specific role.
     * <p>
     * This method fetches a list of users who have a specified role. It is typically used for
     * administrative purposes to retrieve users with specific roles like 'Admin' or 'User'.
     * </p>
     *
     * @param role the role to filter users by (e.g., 'Admin')
     * @param includeReviews flag to indicate whether to include reviews in the user details
     * @return a list of {@link UserDomain} objects representing users with the specified role
     */
    List<UserDomain> getAllUsersWithRole(String role, boolean includeReviews);

    /**
     * Retrieves a user by their unique ID and role.
     * <p>
     * This method fetches a user based on their unique ID and verifies that they have a specific role.
     * It is typically used for verifying role-based access control in the application.
     * </p>
     *
     * @param id the unique identifier of the user
     * @param role the role that the user must have (e.g., 'Admin')
     * @return a {@link UserDomain} object representing the user if found
     */
    UserDomain getUserByIdWithRole(Long id, String role);

    /**
     * Updates a user's details.
     * <p>
     * This method allows modification of a user's profile information. The provided updated user
     * details are validated and applied to the user account.
     * </p>
     *
     * @param userId the unique identifier of the user to be updated
     * @param updatedUser the updated user details
     * @return the {@link UserDomain} object representing the updated user
     */
    UserDomain updateUser(Long userId, UserDomain updatedUser);

    /**
     * Deletes a user account by their unique ID.
     * <p>
     * This method removes a user's account from the system. It is typically used for account
     * termination or administrative cleanup.
     * </p>
     *
     * @param userId the unique identifier of the user to be deleted
     */
    void deleteUser(Long userId);

    /**
     * Changes a user's password.
     * <p>
     * This method allows the user to change their password. It ensures that the old password is
     * validated before the new password is set, to maintain account security.
     * </p>
     *
     * @param userId the unique identifier of the user changing their password
     * @param oldPassword the current password of the user
     * @param newPassword the new password to be set
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Changes a user's email address.
     * <p>
     * This method allows a user to update their email address. The new email is validated for proper
     * format and checked to ensure it doesn't conflict with existing accounts.
     * </p>
     *
     * @param userId the unique identifier of the user changing their email
     * @param newEmail the new email address to be set
     * @return the {@link UserDomain} object representing the user with the updated email
     */
    UserDomain changeEmail(Long userId, String newEmail);

    /**
     * Activates a user account.
     * <p>
     * This method enables a user account that was previously deactivated. It is commonly used by
     * administrators to restore access to a user account.
     * </p>
     *
     * @param userId the unique identifier of the user to be activated
     */
    void activateUser(Long userId);

    /**
     * Deactivates a user account.
     * <p>
     * This method disables a user account, preventing the user from accessing the system.
     * It is typically used for suspensions, account closures, or other administrative actions.
     * </p>
     *
     * @param userId the unique identifier of the user to be deactivated
     */
    void deactivateUser(Long userId);
}
