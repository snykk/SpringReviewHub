package com.example.springreviewhub.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Movie entity in the database.
 * <p>
 * This class maps to the "movies" table and contains attributes related to movie details,
 * along with lifecycle callbacks for managing timestamps.
 * </p>
 */
@Entity
@Table(name = "movies")
@Getter
@Check(constraints = "rating >= 1.0 AND rating <= 10.0") // Ensures the rating is within a valid range
@ToString
public class Movie {

    /**
     * Unique identifier for the movie, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the movie. Cannot be null and has a maximum length of 255 characters.
     */
    @Column(nullable = false, length = 255)
    private String title;

    /**
     * The description of the movie. Stored as text in the database.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * The release date of the movie. Cannot be null.
     */
    @Column(nullable = false)
    private LocalDate releaseDate;

    /**
     * The duration of the movie in minutes. Cannot be null.
     */
    @Column(nullable = false)
    private Integer duration;

    /**
     * The genre of the movie. Cannot be null and has a maximum length of 255 characters.
     */
    @Column(nullable = false, length = 255)
    private String genre;

    /**
     * The director of the movie. Cannot be null and has a maximum length of 255 characters.
     */
    @Column(nullable = false, length = 255)
    private String director;

    /**
     * The rating of the movie, between 1.0 and 10.0, inclusive.
     */
    @Column( precision = 3, scale = 1) // nullable when movie has no reviews yet
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    private BigDecimal rating;

    /**
     * The list of reviews associated with the movie.
     * <p>
     * This field holds a collection of {@link Review} objects, each representing a review
     * written for the movie. The reviews are lazily loaded and associated with the movie
     * via a one-to-many relationship.
     * </p>
     */
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    /**
     * The timestamp when the movie was created. Automatically set on persist and cannot be updated.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The timestamp when the movie was last updated. Automatically set on update.
     */
    @Column()
    // @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * The timestamp when the movie record was deleted. This is nullable and indicates when the movie data was deleted.
     */
    private LocalDateTime deletedAt; // Nullable

    /**
     * Sets the creation and update timestamps before the entity is persisted.
     * This method is called automatically during the persist lifecycle event.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
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
     * Sets the ID of the movie.
     *
     * @param id the ID to set
     * @return the current instance of the Movie object
     */
    public Movie setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title the title to set
     * @return the current instance of the Movie object
     */
    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the description of the movie.
     *
     * @param description the description to set
     * @return the current instance of the Movie object
     */
    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the release date of the movie.
     *
     * @param releaseDate the release date to set
     * @return the current instance of the Movie object
     */
    public Movie setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * Sets the duration of the movie.
     *
     * @param duration the duration in minutes to set
     * @return the current instance of the Movie object
     */
    public Movie setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre the genre to set
     * @return the current instance of the Movie object
     */
    public Movie setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director the director to set
     * @return the current instance of the Movie object
     */
    public Movie setDirector(String director) {
        this.director = director;
        return this;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating the rating to set
     * @return the current instance of the Movie object
     */
    public Movie setRating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Sets the reviews associated with the movie.
     *
     * @param reviews the list of reviews to set
     * @return the current instance of the Movie object
     */
    public Movie setReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    /**
     * Adds a review to the movie's list of reviews.
     *
     * @param review the review to add
     * @return the current instance of the Movie object
     */
    public Movie addReview(Review review) {
        this.reviews.add(review);
        return this;
    }

    /**
     * Sets the creation timestamp of the movie.
     *
     * @param createdAt the creation timestamp to set
     * @return the current instance of the Movie object, allowing for method chaining
     */
    public Movie setCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        }
        return this;
    }

    /**
     * Sets the last update timestamp of the movie.
     *
     * @param updatedAt the last update timestamp to set
     * @return the current instance of the Movie object, allowing for method chaining
     */
    public Movie setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        }
        return this;
    }

    /**
     * Sets the deletion timestamp of the movie.
     *
     * @param deletedAt the timestamp to set when the movie is deleted
     * @return the current instance of the movie object
     */
    public Movie setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
