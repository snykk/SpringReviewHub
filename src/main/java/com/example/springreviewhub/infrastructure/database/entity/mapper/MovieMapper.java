package com.example.springreviewhub.infrastructure.database.entity.mapper;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.infrastructure.database.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    /**
     * Convert `MovieEntity` to `MovieDomain`.
     *
     * @param movie the MovieEntity
     * @return the MovieDomain object
     *
     * This method is used to convert a `Movie` entity (which is typically stored in the database)
     * into a `MovieDomain` object that is used in the application core layer.
     * The conversion process ensures that the domain layer operates with the appropriate business model.
     */
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
                .setUpdatedAt(movie.getUpdatedAt())
                .setDeletedAt(movie.getDeletedAt());
    }

    /**
     * Convert `MovieDomain` to `MovieEntity`.
     *
     * @param movieDomain the MovieDomain
     * @return the MovieEntity object
     *
     * This method is used to convert a `MovieDomain` object (representing the business logic layer)
     * into a `Movie` entity (suitable for database operations).
     * This allows the domain layer's logic to be persisted in the database.
     */
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
                .setRating(movieDomain.getRating())
                .setCreatedAt(movieDomain.getCreatedAt())
                .setUpdatedAt(movieDomain.getUpdatedAt())
                .setDeletedAt(movieDomain.getDeletedAt());
    }

    /**
     * Convert a list of `MovieEntity` objects to a list of `MovieDomain` objects.
     *
     * @param movies the list of MovieEntity objects
     * @return the list of MovieDomain objects
     *
     * This method takes a list of `MovieEntity` objects and converts each entity in the list
     * to a `MovieDomain` object. It uses Java Streams to iterate over the list and convert
     * each element, collecting the results into a new list.
     */
    public static List<MovieDomain> fromEntityListToDomList(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::fromEntityToDomain)
                .collect(Collectors.toList());
    }
}
