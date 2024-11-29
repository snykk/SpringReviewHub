package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Review entities in the database.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * custom queries for interacting with Review entities.
 * </p>
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    /**
     * Retrieves all Review entities based on the user's role.
     * <p>
     * If the role is 'Admin', all reviews are returned, regardless of their `deletedAt` status.
     * Otherwise, only reviews that are not marked as deleted (`deletedAt` is NULL) will be included.
     * </p>
     *
     * @param role the user's role, such as 'Admin'
     * @return a list of Review entities that match the specified criteria
     */
    @Query("SELECT r FROM Review r WHERE (:role = 'Admin' OR r.deletedAt IS NULL)")
    List<Review> findAllWithRole(@Param("role") String role);

    /**
     * Checks whether a Review exists for a specific user and movie combination.
     * <p>
     * This query is used to determine if a user has already provided a review for a given movie.
     * </p>
     *
     * @param userId  the ID of the user
     * @param movieId the ID of the movie
     * @return true if a matching Review exists, false otherwise
     */
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.user.id = :userId AND r.movie.id = :movieId AND r.deletedAt IS NULL")
    boolean existsByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);

    /**
     * Finds a specific Review by its ID, taking the user's role into account.
     * <p>
     * If the role is 'Admin', the query ignores the `deletedAt` field.
     * For other roles, only reviews that are not marked as deleted are returned.
     * </p>
     *
     * @param id   the ID of the Review
     * @param role the user's role, such as 'Admin'
     * @return an Optional containing the Review if found, or empty otherwise
     */
    @Query("SELECT r FROM Review r WHERE r.id = :id AND (:role = 'Admin' OR r.deletedAt IS NULL)")
    Optional<Review> findByIdWithRole(@Param("id") Long id, @Param("role") String role);

    /**
     * Retrieves all Reviews associated with a specific movie, filtered by user role.
     * <p>
     * If the role is 'Admin', all reviews for the movie are included.
     * For other roles, only reviews that are not marked as deleted will be returned.
     * </p>
     *
     * @param movieId the ID of the movie
     * @param role    the user's role, such as 'Admin'
     * @return a list of Review entities matching the criteria
     */
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId AND (:role = 'Admin' OR r.deletedAt IS NULL)")
    List<Review> findByMovieIdWithRole(@Param("movieId") Long movieId, @Param("role") String role);

    /**
     * Retrieves all Reviews submitted by a specific user, filtered by their role.
     * <p>
     * If the role is 'Admin', all reviews from the user are included.
     * For other roles, only reviews that are not marked as deleted will be returned.
     * </p>
     *
     * @param userId the ID of the user
     * @param role   the user's role, such as 'Admin'
     * @return a list of Review entities matching the criteria
     */
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND (:role = 'Admin' OR r.deletedAt IS NULL)")
    List<Review> findByUserIdWithRole(@Param("userId") Long userId, @Param("role") String role);

    /**
     * Finds a Review by the userId and movieId.
     * <p>
     * This query retrieves the review associated with a specific user and movie.
     * </p>
     *
     * @param userId  the ID of the user
     * @param movieId the ID of the movie
     * @return an Optional containing the Review if found, or empty if not found
     */
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.movie.id = :movieId AND r.deletedAt IS NULL")
    Optional<Review> findByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);

    /**
     * Calculates the average rating for a specific movie.
     * <p>
     * This query excludes reviews that are marked as deleted (`deletedAt` is not NULL).
     * </p>
     *
     * @param movieId the ID of the movie
     * @return the average rating as a Double, or null if no valid reviews exist
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId AND r.deletedAt IS NULL")
    Double getAverageRatingByMovieId(@Param("movieId") Long movieId);

    /**
     * Marks a Review entity as deleted by setting the `deletedAt` field to the current timestamp.
     * <p>
     * This method performs a soft delete, ensuring the review remains in the database but is flagged as deleted.
     * </p>
     * <p>
     * It uses `@Modifying` and `@Transactional` annotations to indicate that this is an update operation
     * </p>
     *
     * @param id the ID of the Review to be soft deleted
     */
    @Modifying
    @Transactional
    @Query("UPDATE Review r SET r.deletedAt = CURRENT_TIMESTAMP WHERE r.id = :id")
    void softDeleteReview(@Param("id") Long id);
}
