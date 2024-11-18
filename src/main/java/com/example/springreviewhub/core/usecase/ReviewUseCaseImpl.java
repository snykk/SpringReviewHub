package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.exception.DuplicateReviewException;
import com.example.springreviewhub.core.exception.PermissionIssueException;
import com.example.springreviewhub.core.exception.ReviewNotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.core.interfaces.usecases.IReviewUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ReviewDomain updateReview(Long reviewId, Long userId, ReviewDomain reviewDomain) {
        reviewDomain.setUserId(userId);
        reviewDomain.setId(reviewId);

        Optional<ReviewDomain> existingReviewOpt = reviewRepository.findById(reviewDomain.getId());
        if (existingReviewOpt.isEmpty()) {
            throw new ReviewNotFoundException(String.format("Review with id %s does not exist.", reviewId));
        }
        ReviewDomain existingReview = existingReviewOpt.get();

        if (!existingReview.getUserId().equals(reviewDomain.getUserId())) {
            throw new PermissionIssueException("you don't have access to update this review");
        }

        return reviewRepository.updateReviewAndUpdateMovieRating(reviewDomain);
    }

    @Override
    public List<ReviewDomain> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public ReviewDomain getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found."));
    }

    @Override
    public List<ReviewDomain> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @Override
    public List<ReviewDomain> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Optional<ReviewDomain> reviewOpt = reviewRepository.findById(reviewId);
        if (reviewOpt.isEmpty()) {
            throw new ReviewNotFoundException("Review with ID " + reviewId + " not found.");
        }

        ReviewDomain review = reviewOpt.get();
        if (!review.getUserId().equals(userId)) {
            throw new PermissionIssueException("You do not have permission to delete this review.");
        }

        reviewRepository.deleteById(reviewId);
    }

}
