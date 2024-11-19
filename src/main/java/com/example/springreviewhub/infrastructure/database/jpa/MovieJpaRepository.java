package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing Movie entities in the database.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for the Movie entity.
 * It includes a custom query method for performing an advanced search on movies based on various optional filters.
 * </p>
 */
@Repository
public interface MovieJpaRepository extends JpaRepository<Movie, Long> {

    /**
     * Performs an advanced search on movies based on various filter criteria.
     * <p>
     * This method allows filtering movies by title, genre, minimum rating, release date range, and other criteria.
     * Each parameter is optional and will only be considered if provided. The search is case-insensitive and uses
     * partial matching for title and genre.
     * </p>
     *
     * @param title      the title of the movie to search for (optional)
     * @param genre      the genre of the movie to search for (optional)
     * @param minRating  the minimum rating of the movie (optional)
     * @param startDate  the start date for the movie's release date range (optional)
     * @param endDate    the end date for the movie's release date range (optional)
     * @return a list of movies matching the search criteria
     */
    @Query("SELECT m FROM Movie m WHERE " +
            "(:genre IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%'))) AND " +
            "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:minRating IS NULL OR m.rating >= :minRating) AND " +
            "(:startDate IS NULL OR m.releaseDate >= :startDate) AND " +
            "(:endDate IS NULL OR m.releaseDate <= :endDate)")
    List<Movie> advancedSearch(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate);

}
