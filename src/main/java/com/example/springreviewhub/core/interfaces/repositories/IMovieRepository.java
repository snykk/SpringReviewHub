package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.MovieDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface for the Movie repository.
 * <p>
 * This interface defines the contract for operations related to movie data management.
 * It provides methods for retrieving, creating, updating, deleting, and searching movies.
 * </p>
 */
public interface IMovieRepository {

    /**
     * Retrieves all movies in the repository.
     * <p>
     * This method fetches a list of all movie records.
     * </p>
     *
     * @return a list of all movies
     */
    List<MovieDomain> getAllMoviesWithRole(String role);

    /**
     * Retrieves a movie by its unique identifier.
     * <p>
     * This method attempts to find a movie by its ID. If the movie exists, it returns it wrapped in an Optional,
     * otherwise, it returns an empty Optional.
     * </p>
     *
     * @param id the unique identifier of the movie
     * @return an Optional containing the movie if found, or an empty Optional if not found
     */
    Optional<MovieDomain> getMovieById(Long id);

    /**
     * Retrieves a movie by its unique identifier and role.
     * <p>
     * This method fetches a movie based on its ID and ensures it is associated with the specified role.
     * Useful for scenarios where role-based access is required.
     * </p>
     *
     * @param id   the unique identifier of the movie
     * @param role the role to filter the movie by
     * @return an Optional containing the movie if found and matches the role, or an empty Optional otherwise
     */
    Optional<MovieDomain> getMovieByIdWithRole(Long id, String role);

    /**
     * Creates a new movie in the repository.
     * <p>
     * This method persists a new movie entity in the repository. The movie is returned after being saved.
     * </p>
     *
     * @param movieDomain the movie to be created
     * @return the created movie
     */
    MovieDomain createMovie(MovieDomain movieDomain);

    /**
     * Updates an existing movie in the repository.
     * <p>
     * This method updates the details of a movie identified by its ID. If the movie is found, it is updated with
     * the new values from the provided movie entity and returned.
     * </p>
     *
     * @param id the unique identifier of the movie to be updated
     * @param movieDomain the new movie data
     * @return the updated movie
     */
    MovieDomain updateMovie(Long id, MovieDomain movieDomain);

    /**
     * Performs a soft delete on a movie.
     * <p>
     * This method marks a movie as deleted without physically removing it from the repository.
     * Useful for maintaining historical data while preventing access to the movie in active lists.
     * </p>
     *
     * @param id the unique identifier of the movie to be soft deleted
     */
    void softDelete(Long id);

    /**
     * Searches for movies based on various criteria.
     * <p>
     * This method allows searching for movies based on title, genre, rating, and release date range.
     * The search will return a list of movies matching the specified criteria.
     * </p>
     *
     * @param title the title of the movie (can be partial)
     * @param genre the genre of the movie
     * @param minRating the minimum rating threshold for movies
     * @param startDate the starting release date for the search
     * @param endDate the ending release date for the search
     * @return a list of movies matching the search criteria
     */
    List<MovieDomain> searchMovies(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate);
}
