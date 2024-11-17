package com.example.springreviewhub.core.interfaces.usecases;

import com.example.springreviewhub.core.domain.MovieDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IMovieUseCase {
    List<MovieDomain> getAllMovies();
    MovieDomain getMovieById(Long id);
    MovieDomain createMovie(MovieDomain movieDomain);
    MovieDomain updateMovie(Long id, MovieDomain movieDomain);
    void deleteMovie(Long id);
    List<MovieDomain> searchMovies(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate);
}
