package com.example.springreviewhub.infrastructure.database.repository;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.infrastructure.database.entity.Movie;
import com.example.springreviewhub.infrastructure.database.entity.mapper.MovieMapper;
import com.example.springreviewhub.infrastructure.database.jpa.MovieJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements IMovieRepository {

    private final MovieJpaRepository movieJpaRepository;

    @Autowired
    public MovieRepositoryImpl(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public List<MovieDomain> getAllMoviesWithRole(String role, boolean includeReviews) {
        List<Movie> movieEntities = movieJpaRepository.findAllWithRole(role);

        return MovieMapper.fromEntityListToDomList(movieEntities, includeReviews);
    }

    @Override
    public Optional<MovieDomain> getMovieById(Long id, boolean includeReviews) {
        Optional<Movie> movieEntity = movieJpaRepository.findById(id);

        return movieEntity.map(movie ->  MovieMapper.fromEntityToDomain(movie, includeReviews));
    }

    @Override
    public Optional<MovieDomain> getMovieByIdWithRole(Long id, String role, boolean includeReviews) {
        Optional<Movie> movieEntity = movieJpaRepository.findByIdWithRole(id, role);

        return movieEntity.map(movie -> MovieMapper.fromEntityToDomain(movie, includeReviews));
    }

    @Override
    public MovieDomain createMovie(MovieDomain movieDomain) {
        Movie movie = MovieMapper.fromDomainToEntity(movieDomain);

        Movie savedMovie = movieJpaRepository.save(movie);

        return MovieMapper.fromEntityToDomain(savedMovie, false);
    }

    @Override
    public MovieDomain updateMovie(Long id, MovieDomain movieDomain) {
        Optional<Movie> existingMovie = movieJpaRepository.findById(id);

        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get()
                    .setTitle(movieDomain.getTitle())
                    .setDescription(movieDomain.getDescription())
                    .setReleaseDate(movieDomain.getReleaseDate())
                    .setDuration(movieDomain.getDuration())
                    .setGenre(movieDomain.getGenre())
                    .setDirector(movieDomain.getDirector())
                    .setRating(movieDomain.getRating());

            Movie updatedMovie = movieJpaRepository.save(movie);

            return MovieMapper.fromEntityToDomain(updatedMovie, false);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }

    @Override
    public void softDelete(Long id) {
        movieJpaRepository.softDeleteMovie(id);
    }

    @Override
    public List<MovieDomain> searchMovies(
            String title,
            String genre,
            BigDecimal minRating,
            LocalDate startDate,
            LocalDate endDate,
            boolean includeReviews
    ) {
        List<Movie> movieEntities = movieJpaRepository.advancedSearch(title, genre, minRating, startDate, endDate);

        return MovieMapper.fromEntityListToDomList(movieEntities, includeReviews);
    }
}
