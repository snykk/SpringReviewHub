package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a movie in the system.
 * <p>
 * This class serves as the core domain model for managing movie-related data,
 * including title, description, rating, and other attributes.
 * It features fluent setter methods for concise updates.
 * </p>
 */
@Getter
@ToString
public class MovieDomain {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private BigDecimal rating;
    private Integer duration;
    private String genre;
    private String director;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private List<ReviewDomain> reviews;

    //=========== Chaining Setters ============
    // Each setter returns the current object to allow for method chaining.

    /**
     * Sets the movie ID.
     *
     * @param id the unique identifier for the movie.
     * @return the current instance for method chaining.
     */
    public MovieDomain setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the movie title.
     *
     * @param title the title of the movie.
     * @return the current instance for method chaining.
     */
    public MovieDomain setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the movie description.
     *
     * @param description a brief description or synopsis of the movie.
     * @return the current instance for method chaining.
     */
    public MovieDomain setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the release date of the movie.
     *
     * @param releaseDate the date when the movie was released.
     * @return the current instance for method chaining.
     */
    public MovieDomain setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * Sets the movie's rating.
     *
     * @param rating the average rating of the movie (e.g., 8.5 out of 10).
     * @return the current instance for method chaining.
     */
    public MovieDomain setRating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Sets the duration of the movie.
     *
     * @param duration the duration of the movie in minutes.
     * @return the current instance for method chaining.
     */
    public MovieDomain setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre the genre of the movie (e.g., Action, Drama).
     * @return the current instance for method chaining.
     */
    public MovieDomain setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director the director of the movie.
     * @return the current instance for method chaining.
     */
    public MovieDomain setDirector(String director) {
        this.director = director;
        return this;
    }

    /**
     * Sets the creation timestamp for the movie record.
     *
     * @param createdAt the timestamp when the record was created.
     * @return the current instance for method chaining.
     */
    public MovieDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Sets the last update timestamp for the movie record.
     *
     * @param updatedAt the timestamp when the record was last updated.
     * @return the current instance for method chaining.
     */
    public MovieDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Sets the deleted timestamp.
     *
     * @param deletedAt the timestamp of data deletion.
     * @return the current instance for method chaining.
     */
    public MovieDomain setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public MovieDomain setReviews(List<ReviewDomain> reviews) {
        this.reviews = reviews;
        return this;
    }
}
