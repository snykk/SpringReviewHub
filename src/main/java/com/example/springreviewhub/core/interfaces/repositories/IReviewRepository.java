package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.ReviewDomain;

public interface IReviewRepository {
    ReviewDomain storeReviewAndUpdateMovieRating(ReviewDomain reviewDomain);
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}
