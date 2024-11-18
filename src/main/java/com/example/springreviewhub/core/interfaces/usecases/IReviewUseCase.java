package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;

public interface IReviewUseCase {
    ReviewDomain createReview(Long userId, ReviewDomain reviewDomain);
}
