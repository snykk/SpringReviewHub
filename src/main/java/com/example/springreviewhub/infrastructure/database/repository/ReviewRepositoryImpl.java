package com.example.springreviewhub.infrastructure.database.repository;

import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Review;
import com.example.springreviewhub.infrastructure.database.entity.User;
import com.example.springreviewhub.infrastructure.database.entity.mapper.ReviewMapper;
import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import com.example.springreviewhub.infrastructure.database.jpa.ReviewJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl implements IReviewRepository {

    @Autowired
    private EntityManager entityManager;

    private final ReviewJpaRepository reviewJpaRepository;

    private final MovieJpaRepository movieJpaRepository;

    @Autowired
    public ReviewRepositoryImpl(ReviewJpaRepository reviewJpaRepository, MovieJpaRepository movieJpaRepository) {
        this.reviewJpaRepository = reviewJpaRepository;
        this.movieJpaRepository = movieJpaRepository;
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
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(r) FROM Review r WHERE r.user.id = :userId AND r.movie.id = :movieId", Long.class);
        query.setParameter("userId", userId);
        query.setParameter("movieId", movieId);

        Long count = query.getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public Optional<ReviewDomain> findByUserIdAndMovieId(Long userId, Long movieId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.user.id = :userId AND r.movie.id = :movieId", Review.class);
        query.setParameter("userId", userId);
        query.setParameter("movieId", movieId);

        Review review;
        try {
            review = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }

        return Optional.of(ReviewMapper.fromEntityToDomain(review, true, true));
    }

    @Override
    public Optional<ReviewDomain> findById(Long reviewId) {
        return reviewJpaRepository.findById(reviewId)
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true));
    }


    @Override
    public List<ReviewDomain> findAll() {
        return reviewJpaRepository.findAll().stream()
                .map(review -> ReviewMapper.fromEntityToDomain(review, true, true))
                .toList();
    }

    @Override
    public List<ReviewDomain> findByMovieId(Long movieId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.movie.id = :movieId", Review.class);
        query.setParameter("movieId", movieId);

        return query.getResultList().stream()
                .map(review -> ReviewMapper.fromEntityToDomain(review,true, true))
                .toList();
    }

    @Override
    public List<ReviewDomain> findByUserId(Long userId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.user.id = :userId", Review.class);
        query.setParameter("userId", userId);

        return query.getResultList().stream()
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
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId AND r.deletedAt IS NULL", Double.class);
        query.setParameter("movieId", movieId);
        return query.getSingleResult();
    }

    @Override
    public void softDelete(Long id) {
        reviewJpaRepository.softDeleteMovie(id);
    }
}
