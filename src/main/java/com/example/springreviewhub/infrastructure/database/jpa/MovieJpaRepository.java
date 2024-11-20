package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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


    /**
     * Soft deletes a Movie entity by updating the `deletedAt` timestamp to the current time.
     * <p>
     * This method is annotated with `@Modifying` and `@Transactional`, indicating that it performs
     * an update operation and should be executed within a transactional context.
     * </p>
     *
     * @param id the ID of the Movie to soft delete
     */
    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.deletedAt = CURRENT_TIMESTAMP WHERE m.id = :id")
    void softDeleteMovie(@Param("id") Long id);

    /**
     * Finds all Movie entities that have a specific role or are not marked as deleted.
     * <p>
     * This query filters the Movies by their role (if the role is 'Admin') or by checking if
     * they are not deleted (i.e., `deletedAt` is NULL).
     * </p>
     *
     * @param role the role to filter by, such as 'Admin'
     * @return a list of Movies matching the criteria
     */
    @Query("SELECT m FROM Movie m WHERE (:role = 'Admin' OR m.deletedAt IS NULL)")
    List<Movie> findAllWithRole(@Param("role") String role);

    /**
     * Finds a Movie entity by their ID, but only if the Movie is either an Admin or not marked as deleted.
     * <p>
     * This query ensures that Users who are deleted (i.e., have a non-null `deletedAt` field) are excluded,
     * unless the role provided is 'Admin'.
     * </p>
     *
     * @param id the ID of the Movie to find
     * @param role the role to filter by, such as 'Admin'
     * @return an Optional containing the Movie if found, or empty if no Movie matches the criteria
     */
    @Query("SELECT m FROM Movie m WHERE m.id = :id AND (:role = 'Admin' OR m.deletedAt IS NULL)")
    Optional<Movie> findByIdWithRole(@Param("id") Long id, @Param("role") String role);
}
