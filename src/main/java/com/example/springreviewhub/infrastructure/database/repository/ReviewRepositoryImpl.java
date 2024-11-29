package com.example.springreviewhub.infrastructure.database.repository;

import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Review;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.entity.mapper.ReviewMapper;
import com.example.springreviewhub.infrastructure.database.jpa.ReviewJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl implements IReviewRepository {

    @Autowired
    private EntityManager entityManager;

    private final ReviewJpaRepository reviewJpaRepository;

    @Autowired
    public ReviewRepositoryImpl(ReviewJpaRepository reviewJpaRepository) {
        this.reviewJpaRepository = reviewJpaRepository;
    }

    @Override
    public List<ReviewDomain> findAll() {
        return reviewJpaRepository.findAll().stream()
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true))
                .toList();
    }

    @Override
    public List<ReviewDomain> findAllReviewsWithRole(String role) {
        List<Review> reviewEntities = reviewJpaRepository.findAllWithRole(role);

        return ReviewMapper.fromEntityListToDomList(reviewEntities, true, true);
    }

    @Override
    public ReviewDomain saveReview(ReviewDomain reviewDomain) {
        Review review = ReviewMapper.fromDomainToEntity(reviewDomain);

        Movie managedMovie = entityManager.merge(review.getMovie());
        review.setMovie(managedMovie);

        User managedUser = entityManager.merge(review.getUser());
        review.setUser(managedUser);

        Review savedReview = reviewJpaRepository.save(review);

        return ReviewMapper.fromEntityToDomain(savedReview, false, false);
    }

    @Override
    public boolean existsByUserIdAndMovieId(Long userId, Long movieId) {
        return reviewJpaRepository.existsByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public Optional<ReviewDomain> findByUserIdAndMovieId(Long userId, Long movieId) {
        return reviewJpaRepository.findByUserIdAndMovieId(userId, movieId)
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true));
    }

    @Override
    public Optional<ReviewDomain> findById(Long reviewId) {
        return reviewJpaRepository.findById(reviewId)
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true));
    }

    @Override
    public Optional<ReviewDomain> findByIdWithRole(Long id, String role) {
        Optional<Review> reviewEntity = reviewJpaRepository.findByIdWithRole(id, role);

        return reviewEntity.map(review -> ReviewMapper.fromEntityToDomain(review, true, true));
    }

    @Override
    public List<ReviewDomain> findByMovieIdWithRole(Long movieId, String role) {
        return reviewJpaRepository.findByMovieIdWithRole(movieId, role).stream()
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true))
                .toList();
    }

    @Override
    public List<ReviewDomain> findByUserIdWithRole(Long userId, String role) {
        return reviewJpaRepository.findByUserIdWithRole(userId, role).stream()
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true))
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long reviewId) {
        reviewJpaRepository.deleteById(reviewId);
    }

    @Override
    public Double getAverageRatingByMovieId(Long movieId) {
        return reviewJpaRepository.getAverageRatingByMovieId(movieId);
    }

    @Override
    public void softDelete(Long id) {
        reviewJpaRepository.softDeleteReview(id);
    }
}
