package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.movie.MovieRequest;
import com.example.springreviewhub.adapter.presenter.movie.MovieResponse;
import com.example.springreviewhub.core.domain.MovieDomain;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    /**
     * Convert a `MovieRequest` object to a `MovieDomain` object.
     *
     * @param movieRequest the request object from the API layer containing movie input data
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
     * Convert a `MovieDomain` object to a `MovieResponse` object.
     *
     * @param movieDomain the domain object containing movie data
     * @return a `MovieResponse` object to be sent as an API response
     *
     * This method is used to convert domain models into response objects,
     * which can be returned to the client via the API layer.
     */
    public static MovieResponse fromDomainToMovieResponse(MovieDomain movieDomain) {
        return new MovieResponse(
                movieDomain.getId(),
                movieDomain.getTitle(),
                movieDomain.getDescription(),
                movieDomain.getReleaseDate(),
                movieDomain.getDuration(),
                movieDomain.getGenre(),
                movieDomain.getDirector(),
                movieDomain.getRating()
        );
    }

    /**
     * Convert a list of `MovieDomain` objects to a list of `MovieResponse` objects.
     *
     * @param movieDomains a list of domain-level movie models
     * @return a list of `MovieResponse` objects to be sent as an API response
     *
     * This method is useful for batch operations, such as returning a list of movies
     * in a paginated API response. It leverages streams for efficient mapping.
     */
    public static List<MovieResponse> fromDomainListToResponseList(List<MovieDomain> movieDomains) {
        return movieDomains.stream()
                .map(MovieMapper::fromDomainToMovieResponse)
                .collect(Collectors.toList());
    }
}
