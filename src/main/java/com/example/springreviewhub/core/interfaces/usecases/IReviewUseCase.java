package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;

import java.util.List;
import java.util.Optional;

public interface IReviewUseCase {
    ReviewDomain createReview(Long userId, ReviewDomain reviewDomain);
    ReviewDomain updateReview(Long reviewId, Long userId, ReviewDomain reviewDomain);
    List<ReviewDomain> getAllReviews();
    ReviewDomain getReviewById(Long id);
    List<ReviewDomain> getReviewsByMovieId(Long movieId);
    List<ReviewDomain> getReviewsByUserId(Long userId);
    void deleteReview(Long reviewId, Long userId);

}
