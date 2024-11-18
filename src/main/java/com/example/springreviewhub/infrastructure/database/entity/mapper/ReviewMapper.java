package com.example.springreviewhub.infrastructure.database.entity.mapper;

import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.infrastructure.database.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    /**
     * Convert `ReviewEntity` to `ReviewDomain`.
     *
     * @param review the ReviewEntity
     * @return the ReviewDomain object
     *
     * This method is used to convert a `Review` entity (which is typically stored in the database)
     * into a `ReviewDomain` object that is used in the application core layer.
     * The conversion process ensures that the domain layer operates with the appropriate business model.
     */
    public static ReviewDomain fromEntityToDomain(Review review) {
        if (review == null) {
            return null;
        }
        return new ReviewDomain()
                .setId(review.getId())
                .setText(review.getText())
                .setRating(review.getRating())
                .setMovieId(review.getMovieId())
                .setUserId(review.getUserId())
                .setCreatedAt(review.getCreatedAt())
                .setUpdatedAt(review.getUpdatedAt());
    }

    /**
     * Convert `ReviewDomain` to `ReviewEntity`.
     *
     * @param reviewDomain the ReviewDomain
     * @return the ReviewEntity object
     *
     * This method is used to convert a `ReviewDomain` object (representing the business logic layer)
     * into a `Review` entity (suitable for database operations).
     * This allows the domain layer's logic to be persisted in the database.
     */
    public static Review fromDomainToEntity(ReviewDomain reviewDomain) {
        if (reviewDomain == null) {
            return null;
        }
        return new Review()
                .setId(reviewDomain.getId())
                .setText(reviewDomain.getText())
                .setRating(reviewDomain.getRating())
                .setMovieId(reviewDomain.getMovieId())
                .setUserId(reviewDomain.getUserId())
                .setCreatedAt(reviewDomain.getCreatedAt())
                .setUpdatedAt(reviewDomain.getUpdatedAt());
    }

    /**
     * Convert a list of `ReviewEntity` objects to a list of `ReviewDomain` objects.
     *
     * @param reviews the list of ReviewEntity objects
     * @return the list of ReviewDomain objects
     *
     * This method takes a list of `ReviewEntity` objects and converts each entity in the list
     * to a `ReviewDomain` object. It uses Java Streams to iterate over the list and convert
     * each element, collecting the results into a new list.
     */
    public static List<ReviewDomain> fromEntityListToDomList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewMapper::fromEntityToDomain)
                .collect(Collectors.toList());
    }
}