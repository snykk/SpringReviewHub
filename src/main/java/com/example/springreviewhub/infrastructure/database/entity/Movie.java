package com.example.springreviewhub.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(nullable = false, precision = 3, scale = 1)
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    private BigDecimal rating;

    /**
     * The timestamp when the movie was created. Automatically set on persist and cannot be updated.
     */
    @Column(nullable = false, updatable = false)
    // @Setter(AccessLevel.NONE) // Prevent modification from outside
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The timestamp when the movie was last updated. Automatically set on update.
     */
    @Column()
    // @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();

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
     * Returns a string representation of the movie entity.
     * This includes all attributes of the movie for easy debugging and logging.
     *
     * @return a string containing all movie details
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
