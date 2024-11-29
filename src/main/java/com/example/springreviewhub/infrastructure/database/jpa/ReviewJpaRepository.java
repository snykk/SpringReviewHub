package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for accessing Review entities in the database.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for the Review entity.
 * It allows for basic operations like saving, updating, deleting, and finding Review entities by their ID.
 * </p>
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    /**
     * Soft deletes a Review entity by updating the `deletedAt` timestamp to the current time.
     * <p>
     * This method is annotated with `@Modifying` and `@Transactional`, indicating that it performs
     * an update operation and should be executed within a transactional context.
     * </p>
     *
     * @param id the ID of the Review to soft delete
     */
    @Modifying
    @Transactional
    @Query("UPDATE Review m SET m.deletedAt = CURRENT_TIMESTAMP WHERE m.id = :id")
    void softDeleteMovie(@Param("id") Long id);
}
