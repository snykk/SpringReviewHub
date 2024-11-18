package com.example.springreviewhub.infrastructure.database.jpa;

import com.example.springreviewhub.infrastructure.database.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
