package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.MovieMapper;
import com.example.springreviewhub.adapter.mapper.ReviewMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.review.ReviewRequest;
import com.example.springreviewhub.adapter.presenter.review.ReviewResponse;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.interfaces.usecases.IReviewUseCase;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain createdReview = reviewUseCase.createReview(((Number) claims.get("id")).longValue(), reviewDomain);

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
        ReviewDomain reviewDomain = ReviewMapper.fromReviewRequestToDomain(reviewReq);

        ReviewDomain updatedReview = reviewUseCase.updateReview(id, ((Number) claims.get("id")).longValue(), reviewDomain);

        return ResponseEntity.status(200).body(BaseResponse.success(
                "review data updated successfully",
                ReviewMapper.fromDomainToReviewResponse(updatedReview))
        );
    }
}
