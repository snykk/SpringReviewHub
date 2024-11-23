package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.UserDomain;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the User repository.
 * <p>
 * This interface defines the contract for operations related to user data management.
 * It provides methods for storing, retrieving, and deleting user data, as well as querying users by different criteria.
 * </p>
 */
public interface IUserRepository {

    /**
     * Finds a user by their username with an option to include reviews.
     * <p>
     * This method searches for a user based on their unique username. It returns an `Optional` containing the user
     * if found, otherwise an empty `Optional`. Associated reviews can also be included in the result if specified.
     * </p>
     *
     * @param username      the username of the user to search for
     * @param includeReviews whether to include associated reviews
     * @return an `Optional` containing the user if found, otherwise `Optional.empty()`
     */
    Optional<UserDomain> findByUsername(String username, boolean includeReviews);

    /**
     * Finds a user by their unique identifier (ID).
     * <p>
     * This method searches for a user using their unique ID. It returns an `Optional` containing the user
     * if found, otherwise an empty `Optional`.
     * </p>
     *
     * @param id the unique identifier of the user
     * @return an `Optional` containing the user if found, otherwise `Optional.empty()`
     */
    Optional<UserDomain> findById(Long id);

    /**
     * Finds a user by their ID and role.
     * <p>
     * This method searches for a user based on their unique ID and role. It ensures that the user is either an Admin
     * or has the role specified in the parameter. If the user exists and matches the criteria, it returns the user
     * wrapped in an `Optional`, otherwise an empty `Optional`.
     * </p>
     *
     * @param id the unique identifier of the user
     * @param role the role of the user (e.g., 'Admin')
     * @return an `Optional` containing the user if found and matching the role, otherwise `Optional.empty()`
     */
    Optional<UserDomain> findByIdWithRole(Long id, String role);

    /**
     * Finds a user by their email address.
     * <p>
     * This method searches for a user based on their email address. It returns an `Optional` containing the user
     * if found, otherwise an empty `Optional`.
     * </p>
     *
     * @param email the email address of the user
     * @return an `Optional` containing the user if found, otherwise `Optional.empty()`
     */
    Optional<UserDomain> findByEmail(String email);

    /**
     * Finds all users that have a specific role with an option to include reviews.
     * <p>
     * This method retrieves all users who have the specified role. It returns a list of users with the given role.
     * Reviews associated with the users can also be included if specified.
     * </p>
     *
     * @param role           the role of the users to retrieve (e.g., 'Admin')
     * @param includeReviews whether to include associated reviews
     * @return a list of users who have the specified role
     */
    List<UserDomain> findAllWithRole(String role, boolean includeReviews);

    /**
     * Saves a new user or updates an existing user in the repository.
     * <p>
     * This method stores a user in the repository. If the user already exists (based on their ID or username),
     * it updates the existing user. The saved or updated user is returned.
     * </p>
     *
     * @param user the user to be saved or updated
     * @return the saved or updated user
     */
    UserDomain save(UserDomain user);

    /**
     * Soft deletes a user by setting a flag or marking the user as deleted.
     * <p>
     * This method performs a soft delete on the user by marking them as deleted in the repository. It does not
     * physically remove the user from the database, but rather marks them as deleted.
     * </p>
     *
     * @param id the ID of the user to soft delete
     */
    void softDelete(Long id);
}
