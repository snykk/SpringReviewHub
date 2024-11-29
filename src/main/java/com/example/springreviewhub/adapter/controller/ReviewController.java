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

    @GetMapping
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getAllReviews(
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        List<ReviewDomain> reviews = reviewUseCase.getAllReviewsWithRole(role);

        return ResponseEntity.ok(BaseResponse.success(
                "all review data fetched successfully",
                ReviewMapper.fromDomainListToResponseList(reviews, role)));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
            @RequestBody @Valid ReviewRequest reviewReq,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);
        String role = JwtService.extractRoleFromClaims(claims);

        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain createdReview = reviewUseCase.createReview(userId, reviewDomain);

        return ResponseEntity.status(201).body(BaseResponse.success(
                "review data created successfully",
                ReviewMapper.fromDomainToReviewResponse(createdReview, role))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewRequest reviewReq,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);
        String role = JwtService.extractRoleFromClaims(claims);

        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain updatedReview = reviewUseCase.updateReview(id, userId, reviewDomain);

        return ResponseEntity.status(200).body(BaseResponse.success(
                "review data updated successfully",
                ReviewMapper.fromDomainToReviewResponse(updatedReview, role))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ReviewResponse>> getReviewById(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        ReviewDomain review = reviewUseCase.getReviewByIdWithRole(id, role);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data with id %d fetched successfully", id),
                ReviewMapper.fromDomainToReviewResponse(review, role)));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByMovieId(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        List<ReviewDomain> reviews = reviewUseCase.getReviewsByMovieIdWithRole(id, role);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data by movie id %d fetched successfully", id),
                ReviewMapper.fromDomainListToResponseList(reviews, role)));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<List<ReviewResponse>>> getReviewsByUserId(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        List<ReviewDomain> reviews = reviewUseCase.getReviewsByUserIdWithRole(id, role);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("review data by user id %d fetched successfully", id),
                ReviewMapper.fromDomainListToResponseList(reviews, role)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteReview(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        reviewUseCase.deleteReview(id, userId);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteReviewByMovieId(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = JwtService.extractIdFromClaims(claims);

        reviewUseCase.deleteReviewByMovieId(id, userId);

        return ResponseEntity.status(204).build();
    }
}
