package com.example.springreviewhub.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a Review entity in the database.
 * <p>
 * This class maps to the "reviews" table and contains attributes related to movie reviews,
 * including relationships with movies and users.
 * </p>
 */
@Entity
@Table(name = "reviews")
@Getter
@ToString
public class Review {

    /**
     * Unique identifier for the review, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The text content of the review. Cannot be blank.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Review text must not be blank")
    private String text;

    /**
     * The rating given in the review, between 1 and 10.
     */
    @Column(nullable = false)
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must not exceed 10")
    private Integer rating;

    /**
     * The movie associated with the review.
     * This is a eager-loaded relationship to the Movie entity.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @ToString.Exclude
    private Movie movie;

    /**
     * The user associated with the review.
     * This is a eager-loaded relationship to the User entity.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    /**
     * The timestamp when the review was created. Automatically set on persist and cannot be updated.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The timestamp when the review was last updated. Automatically set on update.
     */
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * The timestamp when the review record was deleted. This is nullable and indicates when the review account was deleted.
     */
    private LocalDateTime deletedAt; // Nullable

    /**
     * Sets the creation and update timestamps before the entity is persisted.
     * This method is called automatically during the persist lifecycle event.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the `updatedAt` timestamp before the entity is updated.
     * This method is called automatically during the update lifecycle event.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the ID of the review.
     *
     * @param id the ID to set
     * @return the current instance of the Review object
     */
    public Review setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the text of the review.
     *
     * @param text the text to set
     * @return the current instance of the Review object
     */
    public Review setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the rating of the review.
     *
     * @param rating the rating to set
     * @return the current instance of the Review object
     */
    public Review setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Sets the movie associated with the review.
     *
     * @param movie the movie to set
     * @return the current instance of the Review object
     */
    public Review setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    /**
     * Sets the user associated with the review.
     *
     * @param user the user to set
     * @return the current instance of the Review object
     */
    public Review setUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets the creation timestamp of the review.
     *
     * @param createdAt the creation timestamp to set
     * @return the current instance of the Review object
     */
    public Review setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Sets the last update timestamp of the review.
     *
     * @param updatedAt the last update timestamp to set
     * @return the current instance of the Review object
     */
    public Review setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Sets the deletion timestamp of the review.
     *
     * @param deletedAt the timestamp to set when the review is deleted
     * @return the current instance of the Review object
     */
    public Review setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
