package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.core.exception.TransactionOperationException;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Review;
import com.example.springreviewhub.infrastructure.database.entity.mapper.ReviewMapper;
import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import com.example.springreviewhub.infrastructure.database.jpa.ReviewJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation = Propagation.REQUIRED) // start db tx using @Transactional
    public ReviewDomain storeReviewAndUpdateMovieRating(ReviewDomain reviewDomain) {
        try {
            Review reviewEntity = ReviewMapper.fromDomainToEntity(reviewDomain);
            reviewJpaRepository.save(reviewEntity);

            TypedQuery<Double> ratingQuery = entityManager.createQuery(
                    "SELECT AVG(r.rating) FROM Review r WHERE r.movieId = :movieId", Double.class);
            ratingQuery.setParameter("movieId", reviewDomain.getMovieId());

            Double avgRating = ratingQuery.getSingleResult();
            if (avgRating == null) {
                avgRating = 0.0;
            }

            BigDecimal avgRatingBigDecimal = BigDecimal.valueOf(avgRating);

            Optional<Movie> movieOpt = movieJpaRepository.findById(reviewDomain.getMovieId());
            if (movieOpt.isPresent()) {
                Movie movie = movieOpt.get();
                movie.setRating(avgRatingBigDecimal);
                movieJpaRepository.save(movie);
            } else {
                throw new TransactionOperationException("movie not found for updating rating.");
            }

            return ReviewMapper.fromEntityToDomain(reviewEntity);
        } catch (Exception e) {
            // if anything goes wrong, transaction will be rolled back automatically
            System.out.println(e.getMessage());
            throw new TransactionOperationException("failed to complete the database transaction.", e);
        }
    }

    @Override
    public boolean existsByUserIdAndMovieId(Long userId, Long movieId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(r) FROM Review r WHERE r.userId = :userId AND r.movieId = :movieId", Long.class);
        query.setParameter("userId", userId);
        query.setParameter("movieId", movieId);

        Long count = query.getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public Optional<ReviewDomain> findByUserIdAndMovieId(Long userId, Long movieId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.userId = :userId AND r.movieId = :movieId", Review.class);
        query.setParameter("userId", userId);
        query.setParameter("movieId", movieId);

        Review review;
        try {
            review = query.getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }

        return Optional.of(ReviewMapper.fromEntityToDomain(review));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReviewDomain updateReviewAndUpdateMovieRating(ReviewDomain reviewDomain) {
        try {
            Optional<Review> existingReviewOpt = reviewJpaRepository.findById(reviewDomain.getId());
            if (existingReviewOpt.isEmpty()) {
                throw new TransactionOperationException("review not found for ID: " + reviewDomain.getId());
            }
            Review existingReview = existingReviewOpt.get();

            existingReview.setText(reviewDomain.getText()).setRating(reviewDomain.getRating());
            reviewJpaRepository.save(existingReview);

            TypedQuery<Double> ratingQuery = entityManager.createQuery(
                    "SELECT AVG(r.rating) FROM Review r WHERE r.movieId = :movieId", Double.class);
            ratingQuery.setParameter("movieId", reviewDomain.getMovieId());
            Double avgRating = ratingQuery.getSingleResult();

            if (avgRating == null) {
                avgRating = 0.0;
            }

            BigDecimal avgRatingBigDecimal = BigDecimal.valueOf(avgRating);

            Optional<Movie> movieOpt = movieJpaRepository.findById(reviewDomain.getMovieId());
            if (movieOpt.isPresent()) {
                Movie movie = movieOpt.get();
                movie.setRating(avgRatingBigDecimal);
                movieJpaRepository.save(movie);
            } else {
                throw new TransactionOperationException("movie not found for updating rating.");
            }

            return ReviewMapper.fromEntityToDomain(existingReview);

        } catch (Exception e) {
            // Handle exceptions and ensure the transaction is rolled back automatically
            System.out.println(e.getMessage());
            throw new TransactionOperationException("failed to update review and movie rating.", e);
        }
    }

    @Override
    public Optional<ReviewDomain> findById(Long reviewId) {
        return reviewJpaRepository.findById(reviewId)
                .map(ReviewMapper::fromEntityToDomain);
    }


    @Override
    public List<ReviewDomain> findAll() {
        return reviewJpaRepository.findAll().stream()
                .map(ReviewMapper::fromEntityToDomain)
                .toList();
    }

    @Override
    public List<ReviewDomain> findByMovieId(Long movieId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.movieId = :movieId", Review.class);
        query.setParameter("movieId", movieId);

        return query.getResultList().stream()
                .map(ReviewMapper::fromEntityToDomain)
                .toList();
    }

    @Override
    public List<ReviewDomain> findByUserId(Long userId) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.userId = :userId", Review.class);
        query.setParameter("userId", userId);

        return query.getResultList().stream()
                .map(ReviewMapper::fromEntityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long reviewId) {
        reviewJpaRepository.deleteById(reviewId);
    }
}
