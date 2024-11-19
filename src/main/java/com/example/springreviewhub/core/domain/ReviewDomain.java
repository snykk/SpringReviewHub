package com.example.springreviewhub.core.domain;

import lombok.Getter;
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
public class ReviewDomain {

    private Long id;
    private String text;
    private Integer rating;
    private Long movieId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
     * Compares this review with another object.
     * Two reviews are considered equal if their IDs, text, rating, movieId, userId, createdAt, and updatedAt are the same.
     *
     * @param o the object to compare this review to
     * @return true if the reviews are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDomain that = (ReviewDomain) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    /**
     * Generates a hash code for the review based on all properties.
     *
     * @return the hash code of this review
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, text, rating, movieId, userId, createdAt, updatedAt);
    }

    /**
     * Provides a string representation of the ReviewDomain object.
     * This is useful for debugging and logging.
     *
     * @return a string representing the review object
     */
    @Override
    public String toString() {
        return "ReviewDomain{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
