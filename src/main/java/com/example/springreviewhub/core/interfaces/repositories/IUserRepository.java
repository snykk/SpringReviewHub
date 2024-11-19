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
     * Finds a user by their username.
     * <p>
     * This method searches for a user based on their unique username. It returns an `Optional` containing the user
     * if found, otherwise an empty `Optional`.
     * </p>
     *
     * @param username the username of the user to search for
     * @return an `Optional` containing the user if found, otherwise `Optional.empty()`
     */
    Optional<UserDomain> findByUsername(String username);

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
     * Retrieves all users from the repository.
     * <p>
     * This method fetches a list of all users stored in the repository.
     * </p>
     *
     * @return a list of all users
     */
    List<UserDomain> findAll();

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
     * Deletes a user by their unique identifier (ID).
     * <p>
     * This method removes a user from the repository based on their unique ID.
     * </p>
     *
     * @param id the unique identifier of the user to be deleted
     */
    void delete(Long id);
}
