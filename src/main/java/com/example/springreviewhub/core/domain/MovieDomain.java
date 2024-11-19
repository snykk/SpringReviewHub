package com.example.springreviewhub.core.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class MovieDomain {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private BigDecimal rating; // Average movie rating (e.g., out of 10)
    private Integer duration; // Duration of the movie in minutes
    private String genre; // Genre of the movie (e.g., Action, Comedy)
    private String director; // Director of the movie
    private LocalDateTime createdAt; // Record creation timestamp
    private LocalDateTime updatedAt; // Record last update timestamp

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
     * Compares this MovieDomain object to another object for equality.
     * The comparison is based on the values of the attributes in both objects.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDomain movie = (MovieDomain) o;

        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(genre, movie.genre) &&
                Objects.equals(director, movie.director) &&
                Objects.equals(createdAt, movie.createdAt) &&
                Objects.equals(updatedAt, movie.updatedAt);
    }

    /**
     * Generates a hash code for the MovieDomain object based on its attributes.
     * This hash code is used in hash-based collections like HashMap and HashSet.
     *
     * @return an integer representing the hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseDate, rating, duration, genre, director, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "MovieDomain{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
