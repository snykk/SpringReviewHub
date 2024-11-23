package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.exception.NotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.core.interfaces.usecases.IMovieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovieUseCaseImpl implements IMovieUseCase {

    private final IMovieRepository movieRepository;

    @Autowired
    public MovieUseCaseImpl(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDomain> getAllMoviesWithRole(String role, boolean includeReviews) {
        return movieRepository.getAllMoviesWithRole(role, includeReviews);
    }

    @Override
    public MovieDomain getMovieById(Long id, boolean includeReviews) {
        return movieRepository.getMovieById(id, includeReviews)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));
    }

    @Override
    public MovieDomain getMovieByIdWithRole(Long id, String role, boolean includeReviews) {
        return movieRepository.getMovieByIdWithRole(id, role, includeReviews)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));
    }

    @Override
    public MovieDomain createMovie(MovieDomain movieDomain) {
        return movieRepository.createMovie(movieDomain);
    }

    @Override
    public MovieDomain updateMovie(Long id, MovieDomain movieDomain) {
        movieRepository.getMovieById(id, false)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));

        return movieRepository.updateMovie(id, movieDomain);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.getMovieById(id, false)

                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));

        movieRepository.softDelete(id);
    }

    @Override
    public List<MovieDomain> searchMovies(
            String title,
            String genre,
            BigDecimal minRating,
            LocalDate startDate,
            LocalDate endDate,
            boolean includeReviews) {
        return movieRepository.searchMovies(title, genre, minRating, startDate, endDate, includeReviews);
    }
}
