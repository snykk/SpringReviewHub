package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the Review repository.
 * <p>
 * This interface defines the contract for operations related to review data management.
 * It provides methods for storing, updating, retrieving, and deleting reviews, as well as querying reviews by different criteria.
 * </p>
 */
public interface IReviewRepository {

    /**
     * Retrieves all reviews from the repository.
     * <p>
     * This method fetches a list of all reviews stored in the repository.
     * </p>
     *
     * @return a list of all reviews
     */
    List<ReviewDomain> findAll();

    /**
     * Retrieves all reviews with a specified role.
     * <p>
     * This method fetches a list of all review records filtered by the specified role.
     * </p>
     *
     * @param role           the role to filter reviews by
     * @return a list of all reviews filtered by role
     */
    List<ReviewDomain> findAllReviewsWithRole(String role);

    /**
     * Saves or updates an existing review in the database.
     * <p>
     * This method maps the provided domain model to an entity, handles any necessary
     * persistence operations (such as managing detached entities), and saves the review record.
     * After saving, the method maps the saved entity back to a domain model for the return value.
     * </p>
     *
     * @param reviewDomain the review domain object containing review data to be saved or updated
     * @return ReviewDomain the saved or updated review as a domain object
     */
    ReviewDomain saveReview(ReviewDomain reviewDomain);

    /**
     * Checks if a review already exists for a specific user and movie.
     * <p>
     * This method checks if a review from a given user for a particular movie already exists in the repository.
     * It returns `true` if the review exists, otherwise `false`.
     * </p>
     *
     * @param userId the ID of the user
     * @param movieId the ID of the movie
     * @return `true` if the review exists, otherwise `false`
     */
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    /**
     * Finds a review by user ID and movie ID.
     * <p>
     * This method retrieves a review by searching for a combination of user ID and movie ID.
     * It returns an `Optional` containing the review if found, otherwise an empty `Optional`.
     * </p>
     *
     * @param userId the ID of the user
     * @param movieId the ID of the movie
     * @return an `Optional` containing the review if found, otherwise `Optional.empty()`
     */
    Optional<ReviewDomain> findByUserIdAndMovieId(Long userId, Long movieId);

    /**
     * Finds a review by its unique identifier.
     * <p>
     * This method searches for a review using its unique review ID and returns an `Optional` containing the review
     * if found, otherwise an empty `Optional`.
     * </p>
     *
     * @param reviewId the unique identifier of the review
     * @return an `Optional` containing the review if found, otherwise `Optional.empty()`
     */
    Optional<ReviewDomain> findById(Long reviewId);

    /**
     * Retrieves a review by its unique identifier and role.
     * <p>
     * This method fetches a review based on its ID and ensures it is filtered with the
     * specified role for role-based access scenarios.
     * </p>
     *
     * @param id             the unique identifier of the review
     * @param role           the role to filter the review by
     * @return an Optional containing the review if found and matches the role, or an empty Optional otherwise
     */
    Optional<ReviewDomain> findByIdWithRole(Long id, String role);


    /**
     * Retrieves all reviews for a specific movie and filtered by role.
     * <p>
     * This method fetches a list of reviews associated with a particular movie ID and ensures it is filtered with the
     * specified role for role-based access scenarios.
     * </p>
     *
     * @param movieId the ID of the movie
     * @param role the role used to filter the reviews (e.g., 'Admin', 'User')
     * @return a list of reviews for the specified movie
     */
    List<ReviewDomain> findByMovieIdWithRole(Long movieId, String role);

    /**
     * Retrieves all reviews by a specific user and filtered by role.
     * <p>
     * This method fetches a list of reviews written by a particular user ID and ensures it is filtered with the
     * specified role for role-based access scenarios.
     * </p>
     *
     * @param userId the ID of the user
     * @param role the role used to filter the reviews (e.g., 'Admin', 'User')
     * @return a list of reviews written by the specified user
     */
    List<ReviewDomain> findByUserIdWithRole(Long userId, String role);

    /**
     * Deletes a review by its unique identifier.
     * <p>
     * This method removes a review from the repository using its review ID.
     * </p>
     *
     * @param reviewId the unique identifier of the review to be deleted
     */
    void deleteById(Long reviewId);

    /**
     * Retrieves the average rating for a specific movie based on its reviews.
     * <p>
     * This method calculates the average rating by querying the database for all reviews
     * associated with the given movie ID and averaging their rating values.
     * If no reviews exist for the movie, the method may return null or a default value.
     * </p>
     *
     * @param movieId the unique identifier of the movie for which the average rating is requested
     * @return Double the calculated average rating for the movie, or null if no ratings are available
     */
    Double getAverageRatingByMovieId(Long movieId);

    /**
     * Performs a soft delete on a review.
     * <p>
     * This method marks a review as deleted without physically removing it from the repository.
     * Useful for maintaining historical data while preventing access to the review in active queries.
     * </p>
     *
     * @param id the unique identifier of the review to be soft deleted
     */
    void softDelete(Long id);
}
