package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.adapter.exception.repository.TransactionOperationException;
import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.exception.DuplicateReviewException;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.core.interfaces.usecases.IReviewUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ReviewUseCaseImpl implements IReviewUseCase {

    private final IReviewRepository reviewRepository;

    @Autowired
    public ReviewUseCaseImpl(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDomain createReview(Long userId, ReviewDomain reviewDomain) {
        reviewDomain.setUserId(userId);

        boolean reviewExists = reviewRepository.existsByUserIdAndMovieId(userId, reviewDomain.getMovieId());
        if (reviewExists) {
            throw new DuplicateReviewException("user has already submitted a review for this movie.");
        }

        return reviewRepository.storeReviewAndUpdateMovieRating(reviewDomain);
    }


}
