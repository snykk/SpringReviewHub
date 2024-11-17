package com.example.springreviewhub.core.interfaces.repositories;

import com.example.springreviewhub.core.domain.MovieDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IMovieRepository {
    List<MovieDomain> getAllMovies();
    Optional<MovieDomain> getMovieById(Long id);
    MovieDomain createMovie(MovieDomain movieDomain);
    MovieDomain updateMovie(Long id, MovieDomain movieDomain);
    void deleteMovie(Long id);
    List<MovieDomain> searchMovies(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate);
}
