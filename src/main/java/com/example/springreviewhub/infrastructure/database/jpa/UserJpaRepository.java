package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing User entities in the database.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for the User entity.
 * It includes methods for finding a User by their username or email.
 * </p>
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by their username.
     *
     * @param username the username of the User to find
     * @return an Optional containing the found User, or empty if no User is found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a User entity by their email.
     *
     * @param email the email of the User to find
     * @return an Optional containing the found User, or empty if no User is found
     */
    Optional<User> findByEmail(String email);
}
