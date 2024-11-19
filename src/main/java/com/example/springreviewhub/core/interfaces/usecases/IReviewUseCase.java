package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;

import java.util.List;

/**
 * Interface for Review Use Cases.
 * <p>
 * This interface defines the operations related to review management within the application.
 * It includes methods for creating, updating, retrieving, and deleting reviews, as well as
 * fetching reviews based on specific criteria.
 * </p>
 */
public interface IReviewUseCase {

    /**
     * Creates a new review for a movie.
     * <p>
     * This method allows a user to submit a review for a specific movie. It also updates
     * the movie's rating based on the new review.
     * </p>
     *
     * @param userId       the ID of the user submitting the review
     * @param reviewDomain the details of the review being created
     * @return the created {@link ReviewDomain} object
     */
    ReviewDomain createReview(Long userId, ReviewDomain reviewDomain);

    /**
     * Updates an existing review.
     * <p>
     * This method allows a user to modify their review for a specific movie. It also updates
     * the movie's rating based on the updated review.
     * </p>
     *
     * @param reviewId     the ID of the review being updated
     * @param userId       the ID of the user making the update
     * @param reviewDomain the updated details of the review
     * @return the updated {@link ReviewDomain} object
     */
    ReviewDomain updateReview(Long reviewId, Long userId, ReviewDomain reviewDomain);

    /**
     * Retrieves all reviews in the system.
     * <p>
     * This method fetches all reviews stored in the system. It can be used to display
     * reviews for administrative purposes or general browsing.
     * </p>
     *
     * @return a list of {@link ReviewDomain} objects representing all reviews
     */
    List<ReviewDomain> getAllReviews();

    /**
     * Retrieves a specific review by its ID.
     * <p>
     * This method fetches the details of a review based on its unique identifier.
     * </p>
     *
     * @param id the unique identifier of the review
     * @return the {@link ReviewDomain} object representing the review
     */
    ReviewDomain getReviewById(Long id);

    /**
     * Retrieves all reviews for a specific movie.
     * <p>
     * This method fetches all reviews associated with a given movie, identified by its ID.
     * It can be used to display user feedback for the movie.
     * </p>
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link ReviewDomain} objects representing the reviews for the movie
     */
    List<ReviewDomain> getReviewsByMovieId(Long movieId);

    /**
     * Retrieves all reviews submitted by a specific user.
     * <p>
     * This method fetches all reviews written by a user, identified by their unique ID.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link ReviewDomain} objects representing the user's reviews
     */
    List<ReviewDomain> getReviewsByUserId(Long userId);

    /**
     * Deletes a review by its ID.
     * <p>
     * This method removes a review from the system, ensuring that only the owner
     * of the review can perform the deletion.
     * </p>
     *
     * @param reviewId the unique identifier of the review to be deleted
     * @param userId   the unique identifier of the user requesting the deletion
     */
    void deleteReview(Long reviewId, Long userId);
}
