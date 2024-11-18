package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.ReviewDomain;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository {
    ReviewDomain storeReviewAndUpdateMovieRating(ReviewDomain reviewDomain);
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    Optional<ReviewDomain> findByUserIdAndMovieId(Long userId, Long movieId);
    ReviewDomain updateReviewAndUpdateMovieRating(ReviewDomain reviewDomain);
    Optional<ReviewDomain> findById(Long reviewId);
    List<ReviewDomain> findAll();
    List<ReviewDomain> findByMovieId(Long movieId);
    List<ReviewDomain> findByUserId(Long userId);
    void deleteById(Long reviewId);

}
