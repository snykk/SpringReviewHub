package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.exception.MovieNotFoundException;
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
    public List<MovieDomain> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    @Override
    public MovieDomain getMovieById(Long id) {
        return movieRepository.getMovieById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public MovieDomain createMovie(MovieDomain movieDomain) {
        return movieRepository.createMovie(movieDomain);
    }

    @Override
    public MovieDomain updateMovie(Long id, MovieDomain movieDomain) {
        movieRepository.getMovieById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        return movieRepository.updateMovie(id, movieDomain);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.getMovieById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        movieRepository.deleteMovie(id);
    }

    @Override
    public List<MovieDomain> searchMovies(String title, String genre, BigDecimal minRating, LocalDate startDate, LocalDate endDate) {
        return movieRepository.searchMovies(title, genre, minRating, startDate, endDate);
    }
}
