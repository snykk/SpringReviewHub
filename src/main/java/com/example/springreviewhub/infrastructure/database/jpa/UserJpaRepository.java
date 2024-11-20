package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.infrastructure.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     * Finds all User entities that have a specific role or are not marked as deleted.
     * <p>
     * This query filters the Users by their role (if the role is 'Admin') or by checking if
     * they are not deleted (i.e., `deletedAt` is NULL).
     * </p>
     *
     * @param role the role to filter by, such as 'Admin'
     * @return a list of Users matching the criteria
     */
    @Query("SELECT u FROM User u WHERE (:role = 'Admin' OR u.deletedAt IS NULL)")
    List<User> findAllWithRole(@Param("role") String role);

    /**
     * Finds a User entity by their ID, but only if the User is either an Admin or not marked as deleted.
     * <p>
     * This query ensures that Users who are deleted (i.e., have a non-null `deletedAt` field) are excluded,
     * unless the role provided is 'Admin'.
     * </p>
     *
     * @param id the ID of the User to find
     * @param role the role to filter by, such as 'Admin'
     * @return an Optional containing the User if found, or empty if no User matches the criteria
     */
    @Query("SELECT u FROM User u WHERE u.id = :id AND (:role = 'Admin' OR u.deletedAt IS NULL)")
    Optional<User> findByIdWithRole(@Param("id") Long id, @Param("role") String role);

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

    /**
     * Soft deletes a User entity by updating the `deletedAt` timestamp to the current time.
     * <p>
     * This method is annotated with `@Modifying` and `@Transactional`, indicating that it performs
     * an update operation and should be executed within a transactional context.
     * </p>
     *
     * @param id the ID of the User to soft delete
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void softDeleteUser(@Param("id") Long id);
}
