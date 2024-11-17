package com.example.springreviewhub.adapter.mapper;

import com.example.springreviewhub.adapter.presenter.movie.MovieRequest;
import com.example.springreviewhub.adapter.presenter.movie.MovieResponse;
import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.infrastructure.database.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    // Domain <-> Entity
    public static MovieDomain fromEntityToDomain(Movie movie) {
        if (movie == null) {
            return null;
        }
        return new MovieDomain()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setDescription(movie.getDescription())
                .setReleaseDate(movie.getReleaseDate())
                .setDuration(movie.getDuration())
                .setGenre(movie.getGenre())
                .setDirector(movie.getDirector())
                .setRating(movie.getRating())
                .setCreatedAt(movie.getCreatedAt())
                .setUpdatedAt(movie.getUpdatedAt());
    }

    public static Movie fromDomainToEntity(MovieDomain movieDomain) {
        if (movieDomain == null) {
            return null;
        }
        return new Movie()
                .setId(movieDomain.getId())
                .setTitle(movieDomain.getTitle())
                .setDescription(movieDomain.getDescription())
                .setReleaseDate(movieDomain.getReleaseDate())
                .setDuration(movieDomain.getDuration())
                .setGenre(movieDomain.getGenre())
                .setDirector(movieDomain.getDirector())
                .setRating(movieDomain.getRating());
    }


    public static List<MovieDomain> fromEntityListToDomList(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::fromEntityToDomain)
                .collect(Collectors.toList());
    }



    // Domain <-> DTO
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

    public static List<MovieResponse> fromDomainListToResponseList(List<MovieDomain> movieDomains) {
        return movieDomains.stream()
                .map(MovieMapper::fromDomainToMovieResponse)
                .collect(Collectors.toList());
    }
}
