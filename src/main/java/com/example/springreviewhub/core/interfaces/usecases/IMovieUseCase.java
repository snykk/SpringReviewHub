package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.MovieDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for Movie Use Cases.
 * <p>
 * This interface defines the operations related to movie management within the application.
 * It includes methods for retrieving, creating, updating, deleting, and searching for movies.
 * </p>
 */
public interface IMovieUseCase {


    List<MovieDomain> getAllMoviesWithRole(String role);

    /**
     * Retrieves details of a specific movie by its ID.
     * <p>
     * This method fetches a single movie's details based on its unique identifier.
     * It ensures that only valid movies are retrieved.
     * </p>
     *
     * @param id the unique identifier of the movie
     * @return the {@link MovieDomain} object representing the movie
     */
    MovieDomain getMovieById(Long id);

    MovieDomain getMovieByIdWithRole(Long id, String role);

    /**
     * Creates a new movie entry in the system.
     * <p>
     * This method handles the creation of a new movie by taking its details and saving them to the system.
     * </p>
     *
     * @param movieDomain the details of the movie to be created
     * @return the {@link MovieDomain} object representing the created movie
     */
    MovieDomain createMovie(MovieDomain movieDomain);

    /**
     * Updates the details of an existing movie.
     * <p>
     * This method modifies the attributes of an existing movie identified by its ID.
     * It ensures that only valid updates are applied.
     * </p>
     *
     * @param id          the unique identifier of the movie to be updated
     * @param movieDomain the updated details of the movie
     * @return the {@link MovieDomain} object representing the updated movie
     */
    MovieDomain updateMovie(Long id, MovieDomain movieDomain);

    /**
     * Deletes a movie by its ID.
     * <p>
     * This method removes a movie from the system based on its unique identifier.
     * It ensures that only valid movies are deleted.
     * </p>
     *
     * @param id the unique identifier of the movie to be deleted
     */
    void deleteMovie(Long id);

    /**
     * Searches for movies based on various criteria.
     * <p>
     * This method allows filtering movies by title, genre, minimum rating, and a date range.
     * It is useful for implementing advanced search functionality for users.
     * </p>
     *
     * @param title     the title of the movie (optional)
     * @param genre     the genre of the movie (optional)
     * @param minRating the minimum rating for filtering movies (optional)
     * @param startDate the start date for the movie release date range (optional)
     * @param endDate   the end date for the movie release date range (optional)
     * @return a list of {@link MovieDomain} objects matching the search criteria
     */
    List<MovieDomain> searchMovies(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate);
}
