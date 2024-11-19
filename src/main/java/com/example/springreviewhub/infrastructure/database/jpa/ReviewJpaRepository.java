package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing Review entities in the database.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for the Review entity.
 * It allows for basic operations like saving, updating, deleting, and finding Review entities by their ID.
 * </p>
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
