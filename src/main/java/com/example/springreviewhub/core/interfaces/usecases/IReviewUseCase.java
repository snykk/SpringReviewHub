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
     * Retrieves all reviews filtered by a specific role.
     * <p>
     * This method fetches a list of all reviews that are associated with the specified role.
     * It can be used to retrieve reviews available to users with a certain role (e.g., Admin, User).
     * </p>
     *
     * @param role the role used to filter the reviews (e.g., 'Admin', 'User')
     * @return a list of {@link ReviewDomain} objects representing reviews associated with the given role
     */
    List<ReviewDomain> getAllReviewsWithRole(String role);

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
     * Retrieves a specific review by its ID and filtered by role.
     * <p>
     * This method fetches the details of a review based on its ID, but it also ensures that the
     * review matches the specified role. This is particularly useful for role-based access control
     * where different roles may have access to different reviews.
     * </p>
     *
     * @param id   the unique identifier of the review
     * @param role the role associated with the review (e.g., 'Admin', 'User')
     * @return the {@link ReviewDomain} object representing the review if found, or an empty Optional if not found
     */
    ReviewDomain getReviewByIdWithRole(Long id, String role);

    /**
     * Retrieves all reviews for a specific movie and filtered by role.
     * <p>
     * This method fetches all reviews associated with a given movie, identified by its ID.
     * It can be used to display user feedback for the movie, but it also ensures that the
     * review matches the specified role. This is particularly useful for role-based access control
     * where different roles may have access to different reviews.
     * </p>
     *
     * @param movieId the unique identifier of the movie
     * @param role the role associated with the review (e.g., 'Admin', 'User')
     * @return a list of {@link ReviewDomain} objects representing the reviews for the movie
     */
    List<ReviewDomain> getReviewsByMovieIdWithRole(Long movieId, String role);

    /**
     * Retrieves all reviews submitted by a specific user.
     * <p>
     * This method fetches all reviews written by a user, identified by their unique ID.
     * It also ensures that the review matches the specified role. This is particularly useful for role-based access control
     * where different roles may have access to different reviews.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @param role the role associated with the review (e.g., 'Admin', 'User')
     * @return a list of {@link ReviewDomain} objects representing the user's reviews
     */
    List<ReviewDomain> getReviewsByUserIdWithRole(Long userId, String role);

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

    void deleteReviewByMovieId(Long movieId, Long userId);
}
