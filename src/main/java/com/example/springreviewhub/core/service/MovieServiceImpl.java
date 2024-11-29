package com.example.springreviewhub.core.service;

import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.exception.NotFoundException;
import com.example.springreviewhub.core.interfaces.repositories.IMovieRepository;
import com.example.springreviewhub.core.interfaces.repositories.IReviewRepository;
import com.example.springreviewhub.core.interfaces.services.IMovieService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// it's not best practice though
@Service
public class MovieServiceImpl implements IMovieService {
    private final IMovieRepository movieRepository;
    private final IReviewRepository reviewRepository;

    public MovieServiceImpl(IMovieRepository movieRepository, IReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    public void refreshMovieRating(Long movieId) {
        Double avgRating = reviewRepository.getAverageRatingByMovieId(movieId);
        if (avgRating == null) {
            avgRating = 0.0;
        }

        MovieDomain movieDomain = movieRepository.getMovieById(movieId, false)
                .orElseThrow(() -> new NotFoundException("Movie not found."));
        movieDomain.setRating(BigDecimal.valueOf(avgRating));
        movieRepository.updateMovie(movieId, movieDomain);
    }
}
