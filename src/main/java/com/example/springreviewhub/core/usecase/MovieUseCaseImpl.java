package com.example.springreviewhub.core.usecase;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.domain.ReviewDomain;
import com.example.springreviewhub.core.domain.Role;
import com.example.springreviewhub.core.exception.NotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.core.interfaces.usecases.IMovieUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieUseCaseImpl implements IMovieUseCase {

    private final IMovieRepository movieRepository;

    private final IReviewRepository reviewRepository;

    @Autowired
    public MovieUseCaseImpl(IMovieRepository movieRepository, IReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<MovieDomain> getAllMoviesWithRole(String role, boolean includeReviews) {
        List<MovieDomain> movieDomains = movieRepository.findAllMoviesWithRole(role, includeReviews);

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
        return movieRepository.findMovieById(id, includeReviews)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));
    }

    @Override
    public MovieDomain getMovieByIdWithRole(Long id, String role, boolean includeReviews) {
        MovieDomain movieDomain = movieRepository.findMovieByIdWithRole(id, role, includeReviews)
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
        movieRepository.findMovieById(id, false)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));

        return movieRepository.updateMovie(id, movieDomain);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        MovieDomain movieDomain = movieRepository.findMovieById(id, true)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with ID %d not found.", id)));

        List<ReviewDomain> reviews = movieDomain.getReviews();

        reviews.forEach(review -> reviewRepository.softDelete(review.getId()));

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
