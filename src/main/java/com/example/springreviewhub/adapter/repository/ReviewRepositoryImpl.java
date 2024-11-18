package com.example.springreviewhub.adapter.repository;

import com.example.springreviewhub.adapter.exception.repository.TransactionOperationException;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.Review;
import com.example.springreviewhub.infrastructure.database.entity.mapper.ReviewMapper;
import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import com.example.springreviewhub.infrastructure.database.jpa.ReviewJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                throw new TransactionOperationException("Movie not found for updating rating.");
            }

            return ReviewMapper.fromEntityToDomain(reviewEntity);
        } catch (Exception e) {
            // if anything goes wrong, transaction will be rolled back automatically
            System.out.println(e.getMessage());
            throw new TransactionOperationException("Failed to complete the database transaction.", e);
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
}
