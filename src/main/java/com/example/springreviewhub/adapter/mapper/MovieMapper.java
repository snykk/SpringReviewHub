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
     * Convert a `MovieRequest` object to a `MovieDomain` object.
     *
     * @param movieRequest the request object from the Controller layer containing movie input data
     * @return a `MovieDomain` object representing the domain-level movie model
     *
     * This method is typically used to convert incoming API requests into domain models,
     * which can be processed by the core application logic.
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
     * Convert a `MovieDomain` object to a `MovieResponse` object based on user role.
     *
     * @param movieDomain the domain-level movie model
     * @param role        the role of the user (e.g., "Admin" or "Reviewer")
     * @return a `MovieResponse` object or its extended version for Admin
     */
    public static MovieResponse fromDomainToMovieResponse(MovieDomain movieDomain, String role) {
        if (movieDomain == null) {
            return null;
        }

        // Instantiate response based on role
        MovieResponse response = role.equals(Role.Admin.name())
                ? new MovieExtendedResponse()
                : new MovieResponse();

        // Common properties
        response.setId(movieDomain.getId())
                .setTitle(movieDomain.getTitle())
                .setDescription(movieDomain.getDescription())
                .setReleaseDate(movieDomain.getReleaseDate())
                .setDuration(movieDomain.getDuration())
                .setGenre(movieDomain.getGenre())
                .setDirector(movieDomain.getDirector())
                .setRating(movieDomain.getRating())
                .setCreatedAt(movieDomain.getCreatedAt())
                .setUpdatedAt(movieDomain.getUpdatedAt());

        // Admin-specific properties
        if (response instanceof MovieExtendedResponse) {
            ((MovieExtendedResponse) response).setDeletedAt(movieDomain.getDeletedAt());
        }

        return response;
    }

    /**
     * Convert a list of `MovieDomain` objects to a list of `MovieResponse` objects based on user role.
     *
     * @param movieDomains a list of domain-level movie models
     * @param role         the role of the user (e.g., "Admin" or "Reviewer")
     * @return a list of `MovieResponse` objects to be sent as an API response
     */
    public static List<MovieResponse> fromDomainListToResponseList(List<MovieDomain> movieDomains, String role) {
        return movieDomains.stream()
                .map(movieDomain -> fromDomainToMovieResponse(movieDomain, role))
                .collect(Collectors.toList());
    }
}
