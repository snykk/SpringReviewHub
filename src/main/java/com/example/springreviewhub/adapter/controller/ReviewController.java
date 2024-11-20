package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.ReviewMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.review.ReviewRequest;
import com.example.springreviewhub.adapter.presenter.review.ReviewResponse;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.usecases.IReviewUseCase;
import com.example.springreviewhub.infrastructure.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Validated
public class ReviewController {

    private final IReviewUseCase reviewUseCase;

    @Autowired
    public ReviewController(IReviewUseCase reviewUseCase) {
        this.reviewUseCase = reviewUseCase;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
            @RequestBody @Valid ReviewRequest reviewReq,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain createdReview = reviewUseCase.createReview(userId, reviewDomain);

        return ResponseEntity.status(201).body(BaseResponse.success(
                "review data created successfully",
                ReviewMapper.fromDomainToReviewResponse(createdReview))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewRequest reviewReq,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain updatedReview = reviewUseCase.updateReview(id, userId, reviewDomain);

        return ResponseEntity.status(200).body(BaseResponse.success(
                "review data updated successfully",
                ReviewMapper.fromDomainToReviewResponse(updatedReview))
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getAllReviews() {
        List<ReviewResponse> reviews = reviewUseCase.getAllReviews().stream()
                .map(ReviewMapper::fromDomainToReviewResponse)
                .toList();

        return ResponseEntity.ok(BaseResponse.success(
                "all review data fetched successfully",
                reviews));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ReviewResponse>> getReviewById(
            @PathVariable Long id
    ) {
        ReviewDomain review = reviewUseCase.getReviewById(id);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data with id %d fetched successfully", id),
                ReviewMapper.fromDomainToReviewResponse(review)));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByMovieId(
            @PathVariable Long id
    ) {
        List<ReviewResponse> reviews = reviewUseCase.getReviewsByMovieId(id).stream()
                .map(ReviewMapper::fromDomainToReviewResponse)
                .toList();

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data by movie id %d fetched successfully", id),
                reviews));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByUserId(
            @PathVariable Long id
    ) {
        List<ReviewResponse> reviews = reviewUseCase.getReviewsByUserId(id).stream()
                .map(ReviewMapper::fromDomainToReviewResponse)
                .toList();

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data by user id %d fetched successfully", id),
                reviews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteReview(@PathVariable Long id, @AuthenticationPrincipal Claims claims) {
        Long userId = JwtService.extractIdFromClaims(claims);

        reviewUseCase.deleteReview(id, userId);

        return ResponseEntity.status(204).build();
    }

}
