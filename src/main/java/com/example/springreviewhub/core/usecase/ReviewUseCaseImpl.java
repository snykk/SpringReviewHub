package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.domain.UserDomain;
import com.example.springreviewhub.core.exception.*;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.core.interfaces.repositories.IUserRepository;
import com.example.springreviewhub.core.interfaces.usecases.IReviewUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewUseCaseImpl implements IReviewUseCase {

    private final IReviewRepository reviewRepository;

    private final IUserRepository userRepository;

    private final IMovieRepository movieRepository;

    @Autowired
    public ReviewUseCaseImpl(IReviewRepository reviewRepository, IUserRepository userRepository, IMovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ReviewDomain createReview(Long userId, ReviewDomain reviewDomain) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %d not found.", userId)));
        reviewDomain.setUser(user);

        MovieDomain movie = movieRepository.getMovieById(reviewDomain.getMovieId(), false)
                .orElseThrow(() -> new NotFoundException(String.format("Review with ID %d not found.", reviewDomain.getMovieId())));
        reviewDomain.setMovie(movie);

        boolean reviewExists = reviewRepository.existsByUserIdAndMovieId(userId, reviewDomain.getMovieId());
        if (reviewExists) {
            throw new DuplicateReviewException("User has already submitted a review for this movie.");
        }

        return reviewRepository.storeReviewAndUpdateMovieRating(reviewDomain);
    }

    @Override
    public ReviewDomain updateReview(Long reviewId, Long userId, ReviewDomain reviewDomain) {

        Optional<ReviewDomain> existingReviewOpt = reviewRepository.findById(reviewId);
        if (existingReviewOpt.isEmpty()) {
            throw new NotFoundException(String.format("Review with ID %d not found.", reviewId));
        }
        ReviewDomain existingReview = existingReviewOpt.get();

        if (!existingReview.getUserId().equals(userId)) {
            throw new PermissionIssueException("You don't have permission to update this review");
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
                .orElseThrow(() -> new NotFoundException(String.format("Review with ID %d not found.", id)));
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
            throw new NotFoundException(String.format("Review with ID %d not found.", reviewId));
        }

        ReviewDomain review = reviewOpt.get();
        if (!review.getUserId().equals(userId)) {
            throw new PermissionIssueException("You do not have permission to delete this review.");
        }

        reviewRepository.deleteById(reviewId);
    }

}
