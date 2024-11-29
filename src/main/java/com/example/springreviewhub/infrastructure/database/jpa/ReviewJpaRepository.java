package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing Review entities in the database.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for the Review entity.
 * It allows for basic operations like saving, updating, deleting, and finding Review entities by their ID.
 * </p>
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    /**
     * Finds all Movie entities that have a specific role or are not marked as deleted.
     * <p>
     * This query filters the Movies by their role (if the role is 'Admin') or by checking if
     * they are not deleted (i.e., `deletedAt` is NULL).
     * </p>
     *
     * @param role the role to filter by, such as 'Admin'
     * @return a list of Movies matching the criteria
     */
    @Query("SELECT r FROM Review r WHERE (:role = 'Admin' OR r.deletedAt IS NULL)")
    List<Review> findAllWithRole(@Param("role") String role);

    /**
     * Finds a Review entity by their ID, but only if the Review is either an Admin or not marked as deleted.
     * <p>
     * This query ensures that Users who are deleted (i.e., have a non-null `deletedAt` field) are excluded,
     * unless the role provided is 'Admin'.
     * </p>
     *
     * @param id the ID of the Review to find
     * @param role the role to filter by, such as 'Admin'
     * @return an Optional containing the Review if found, or empty if no Review matches the criteria
     */
    @Query("SELECT r FROM Review r WHERE r.id = :id AND (:role = 'Admin' OR r.deletedAt IS NULL)")
    Optional<Review> findByIdWithRole(@Param("id") Long id, @Param("role") String role);

    /**
     * Soft deletes a Review entity by updating the `deletedAt` timestamp to the current time.
     * <p>
     * This method is annotated with `@Modifying` and `@Transactional`, indicating that it performs
     * an update operation and should be executed within a transactional context.
     * </p>
     *
     * @param id the ID of the Review to soft delete
     */
    @Modifying
    @Transactional
    @Query("UPDATE Review m SET m.deletedAt = CURRENT_TIMESTAMP WHERE m.id = :id")
    void softDeleteMovie(@Param("id") Long id);
}
