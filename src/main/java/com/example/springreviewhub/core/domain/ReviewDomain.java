package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a review for a movie.
 * <p>
 * This class is part of the domain model for movie review management. It holds details such as the review text,
 * rating, associated movie and user IDs, and timestamps for creation and updates.
 * </p>
 */
@Getter
@ToString
public class ReviewDomain {

    private Long id;
    private String text;
    private Integer rating;
    private Long movieId;
    private MovieDomain movie;
    private Long userId;
    private UserDomain user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    //=========== Chaining Setters =============

    /**
     * Sets the unique identifier for the review.
     *
     * @param id the unique identifier for the review
     * @return the current instance for method chaining
     */
    public ReviewDomain setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the content of the review.
     *
     * @param text the review text
     * @return the current instance for method chaining
     */
    public ReviewDomain setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the rating for the review.
     *
     * @param rating the rating (e.g., a value between 1 and 5)
     * @return the current instance for method chaining
     */
    public ReviewDomain setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Sets the movie ID for the review.
     *
     * @param movieId the ID of the movie being reviewed
     * @return the current instance for method chaining
     */
    public ReviewDomain setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public ReviewDomain setMovie(MovieDomain movie) {
        this.movie = movie;
        return this;
    }

    /**
     * Sets the user ID for the review.
     *
     * @param userId the ID of the user who wrote the review
     * @return the current instance for method chaining
     */
    public ReviewDomain setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public ReviewDomain setUser(UserDomain user) {
        this.user = user;
        return this;
    }

    /**
     * Sets the creation timestamp for the review.
     *
     * @param createdAt the timestamp when the review was created
     * @return the current instance for method chaining
     */
    public ReviewDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Sets the last update timestamp for the review.
     *
     * @param updatedAt the timestamp of the last review update
     * @return the current instance for method chaining
     */
    public ReviewDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Sets the deletion timestamp for the review.
     *
     * @param deletedAt the timestamp when the review was deleted
     * @return the current instance for method chaining
     */
    public ReviewDomain setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
