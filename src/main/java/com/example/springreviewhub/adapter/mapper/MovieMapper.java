package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.movie.MovieExtendedResponse;
import com.example.springreviewhub.adapter.presenter.movie.MovieRequest;
import com.example.springreviewhub.adapter.presenter.movie.MovieResponse;
import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.Role;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    /**
     * Converts a `MovieRequest` object to a `MovieDomain` object.
     *
     * @param movieRequest the request object from the Controller layer containing movie input data
     * @return a `MovieDomain` object representing the domain-level movie model
     *
     * This method is used to transform incoming API request data into a domain object
     * that is processed within the core business logic.
     */
    public static MovieDomain fromMovieRequestToDomain(MovieRequest movieRequest) {
        if (movieRequest == null) {
            return null;
        }
        return new MovieDomain()
                .setTitle(movieRequest.getTitle())
                .setDescription(movieRequest.getDescription())
                .setReleaseDate(movieRequest.getReleaseDate())
                .setRating(movieRequest.getRating())
                .setDuration(movieRequest.getDuration())
                .setGenre(movieRequest.getGenre())
                .setDirector(movieRequest.getDirector());
    }

    /**
     * Converts a `MovieDomain` object to a `MovieResponse` object, considering user roles.
     *
     * @param movieDomain     the domain-level movie model
     * @param role            the role of the user (e.g., "Admin" or "Reviewer")
     * @param isIncludeReviews flag indicating whether to include reviews in the response
     * @return a `MovieResponse` object for Reviewer or an extended `MovieExtendedResponse` for Admin
     *
     * This method determines the appropriate response type based on the user's role
     * and includes additional properties for Admin users.
     */
    public static MovieResponse fromDomainToMovieResponse(MovieDomain movieDomain, String role, boolean isIncludeReviews) {
        if (movieDomain == null) {
            return null;
        }

        // Determine the response type based on the role
        MovieResponse response = role.equals(Role.Admin.name())
                ? new MovieExtendedResponse()
                : new MovieResponse();

        // Map common properties
        response.setId(movieDomain.getId())
                .setTitle(movieDomain.getTitle())
                .setDescription(movieDomain.getDescription())
                .setReleaseDate(movieDomain.getReleaseDate())
                .setDuration(movieDomain.getDuration())
                .setGenre(movieDomain.getGenre())
                .setDirector(movieDomain.getDirector())
                .setRating(movieDomain.getRating())
                .setCreatedAt(movieDomain.getCreatedAt())
                .setUpdatedAt(movieDomain.getUpdatedAt())

                .setReviews(isIncludeReviews ?
                        ReviewMapper.fromDomainListToResponseList(movieDomain.getReviews(), role)
                        : null);

        // Map Admin-specific properties
        if (response instanceof MovieExtendedResponse) {
            ((MovieExtendedResponse) response).setDeletedAt(movieDomain.getDeletedAt());
        }

        return response;
    }

    /**
     * Converts a list of `MovieDomain` objects to a list of `MovieResponse` objects, considering user roles.
     *
     * @param movieDomains     a list of domain-level movie models
     * @param role             the role of the user (e.g., "Admin" or "Reviewer")
     * @param isIncludeReviews flag indicating whether to include reviews in the responses
     * @return a list of `MovieResponse` objects to be returned as an API response
     *
     * This method maps each domain object in the input list to a corresponding response object,
     * applying role-specific transformations as needed.
     */
    public static List<MovieResponse> fromDomainListToResponseList(List<MovieDomain> movieDomains, String role, boolean isIncludeReviews) {
        return movieDomains.stream()
                .map(movieDomain -> fromDomainToMovieResponse(movieDomain, role, isIncludeReviews))
                .collect(Collectors.toList());
    }
}
