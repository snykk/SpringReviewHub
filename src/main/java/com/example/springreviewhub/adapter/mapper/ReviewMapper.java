package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.movie.MovieExtendedResponse;
import com.example.springreviewhub.adapter.presenter.review.ReviewExtendedResponse;
import com.example.springreviewhub.adapter.presenter.review.ReviewRequest;
import com.example.springreviewhub.adapter.presenter.review.ReviewResponse;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.domain.Role;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    /**
     * Convert a `ReviewRequest` object to a `ReviewDomain` object.
     *
     * @param reviewRequest the request object from the Controller layer containing review input data
     * @return a `ReviewDomain` object representing the domain-level review model
     *
     * This method is typically used to convert incoming API requests into domain models,
     * which can be processed by the core application logic.
     */
    public static ReviewDomain fromReviewRequestToDomain(ReviewRequest reviewRequest) {
        if (reviewRequest == null) {
            return null;
        }
        return new ReviewDomain()
                .setText(reviewRequest.getText())
                .setRating(reviewRequest.getRating())
                .setMovieId(reviewRequest.getMovieId());
    }

    /**
     * Convert a `ReviewDomain` object to a `ReviewResponse` object.
     *
     * @param reviewDomain the domain object containing review data
     * @return a `ReviewResponse` object to be sent as an API response
     *
     * This method is used to convert domain models into response objects,
     * which can be returned to the client via the Controller layer.
     */
    public static ReviewResponse fromDomainToReviewResponse(ReviewDomain reviewDomain, String role) {
        if (reviewDomain == null) {
            return null;
        }

        // Determine the response type based on the role
        ReviewResponse response = role.equals(Role.Admin.name())
                ? new ReviewExtendedResponse()
                : new ReviewResponse();

        response.setId(reviewDomain.getId())
                .setText(reviewDomain.getText())
                .setRating(reviewDomain.getRating())
                .setMovieId(reviewDomain.getMovieId())
                .setUserId(reviewDomain.getUserId())
                .setCreatedAt(reviewDomain.getCreatedAt())
                .setUpdatedAt(reviewDomain.getUpdatedAt());

        // Map Admin-specific properties
        if (response instanceof ReviewExtendedResponse) {
            ((ReviewExtendedResponse) response).setDeletedAt(reviewDomain.getDeleteddAt());
        }

        return response;
    }

    /**
     * Convert a list of `ReviewDomain` objects to a list of `ReviewResponse` objects.
     *
     * @param reviewDomains a list of domain-level review models
     * @return a list of `ReviewResponse` objects to be sent as an API response
     *
     * This method is useful for batch operations, such as returning a list of reviews
     * in a paginated API response. It leverages streams for efficient mapping.
     */
    public static List<ReviewResponse> fromDomainListToResponseList(List<ReviewDomain> reviewDomains, String role) {
        return reviewDomains.stream()
                .map(reviewDomain -> fromDomainToReviewResponse(reviewDomain, role))
                .collect(Collectors.toList());
    }
}
