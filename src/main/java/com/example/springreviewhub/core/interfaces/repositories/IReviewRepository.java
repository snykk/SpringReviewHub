package com.example.springreviewhub.core.interfaces.repositories;

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
     * Stores a new review and updates the associated movie's rating.
     * <p>
     * This method saves a new review to the repository and concurrently updates the movie's rating
     * based on the review provided. The updated review is returned.
     * </p>
     *
     * @param reviewDomain the review to be stored
     * @return the stored review with updated movie rating
     */
    ReviewDomain storeReviewAndUpdateMovieRating(ReviewDomain reviewDomain);

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
     * Updates an existing review and updates the associated movie's rating.
     * <p>
     * This method updates an existing review in the repository and concurrently updates the movie's rating
     * based on the new review content. The updated review is returned.
     * </p>
     *
     * @param reviewDomain the review with updated data
     * @return the updated review with the new movie rating
     */
    ReviewDomain updateReviewAndUpdateMovieRating(ReviewDomain reviewDomain);

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
     * Retrieves all reviews from the repository.
     * <p>
     * This method fetches a list of all reviews stored in the repository.
     * </p>
     *
     * @return a list of all reviews
     */
    List<ReviewDomain> findAll();

    /**
     * Retrieves all reviews for a specific movie.
     * <p>
     * This method fetches a list of reviews associated with a particular movie ID.
     * </p>
     *
     * @param movieId the ID of the movie
     * @return a list of reviews for the specified movie
     */
    List<ReviewDomain> findByMovieId(Long movieId);

    /**
     * Retrieves all reviews by a specific user.
     * <p>
     * This method fetches a list of reviews written by a particular user.
     * </p>
     *
     * @param userId the ID of the user
     * @return a list of reviews written by the specified user
     */
    List<ReviewDomain> findByUserId(Long userId);

    /**
     * Deletes a review by its unique identifier.
     * <p>
     * This method removes a review from the repository using its review ID.
     * </p>
     *
     * @param reviewId the unique identifier of the review to be deleted
     */
    void deleteById(Long reviewId);
}
