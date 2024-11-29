package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.core.exception.NotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.core.interfaces.usecases.IMovieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieUseCaseImpl implements IMovieUseCase {

    private final IMovieRepository movieRepository;

    @Autowired
    public MovieUseCaseImpl(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDomain> getAllMoviesWithRole(String role, boolean includeReviews) {
        List<MovieDomain> movieDomains = movieRepository.getAllMoviesWithRole(role, includeReviews);

        if (Role.Reviewer.name().equalsIgnoreCase(role)) {
            movieDomains.forEach(movie -> {
                List<ReviewDomain> filteredReviews = movie.getReviews().stream()
                        .filter(review -> review.getDeletedAt() == null)
                        .collect(Collectors.toList());
                movie.setReviews(filteredReviews);
            });
        }

        return movieDomains;
    }


    @Override
    public MovieDomain getMovieById(Long id, boolean includeReviews) {
        return movieRepository.getMovieById(id, includeReviews)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));
    }

    @Override
    public MovieDomain getMovieByIdWithRole(Long id, String role, boolean includeReviews) {
        MovieDomain movieDomain = movieRepository.getMovieByIdWithRole(id, role, includeReviews)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));

        if (Role.Reviewer.name().equalsIgnoreCase(role)) {
            List<ReviewDomain> filteredReviews = movieDomain.getReviews().stream()
                    .filter(review -> review.getDeletedAt() == null)
                    .collect(Collectors.toList());
            movieDomain.setReviews(filteredReviews);
        };

        return movieDomain;
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
            String role,
            String title,
            String genre,
            BigDecimal minRating,
            LocalDate startDate,
            LocalDate endDate,
            boolean includeReviews) {
        return movieRepository.searchMovies(role, title, genre, minRating, startDate, endDate, includeReviews);
    }
}
