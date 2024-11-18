package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.MovieMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.movie.MovieResponse;
import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.interfaces.usecases.IMovieUseCase;
import com.example.springreviewhub.adapter.presenter.movie.MovieRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Validated
public class MovieController {

    private final IMovieUseCase movieUseCase;

    @Autowired
    public MovieController(IMovieUseCase movieUseCase) {
        this.movieUseCase = movieUseCase;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MovieResponse>>> getAllMovies() {
        List<MovieDomain> movies = movieUseCase.getAllMovies();

        List<MovieResponse> responses = MovieMapper.fromDomainListToResponseList(movies);
        return ResponseEntity.ok(BaseResponse.success(
                "movies data fetched successfully",
                responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponse>> getMovieById(@PathVariable Long id) {
        MovieDomain movie = movieUseCase.getMovieById(id);

        MovieResponse movieResponse = MovieMapper.fromDomainToMovieResponse(movie);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("Movie data with id %d fetched successfully", id),
                movieResponse));

    }

    @PostMapping
    public ResponseEntity<BaseResponse<MovieResponse>> createMovie(@RequestBody @Valid MovieRequest movieReq) {
        MovieDomain movieDomain = MovieMapper.fromMovieRequestToDomain(movieReq);

        MovieDomain createdMovie = movieUseCase.createMovie(movieDomain);

        return ResponseEntity.status(201).body(BaseResponse.success(
                "movie data create successfully",
                MovieMapper.fromDomainToMovieResponse(createdMovie))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponse>> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieRequest movieReq) {
        MovieDomain movieDomain = MovieMapper.fromMovieRequestToDomain(movieReq);

        MovieDomain updatedMovie = movieUseCase.updateMovie(id, movieDomain);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("Movie with id %d has been successfully updated", id),
                MovieMapper.fromDomainToMovieResponse(updatedMovie)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieUseCase.deleteMovie(id);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<MovieResponse>>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (minRating != null && (minRating.compareTo(BigDecimal.ONE) < 0 || minRating.compareTo(BigDecimal.TEN) > 0)) {
            throw new IllegalArgumentException("minRating must be between 1.0 and 10.0");
        }

        List<MovieDomain> movies = movieUseCase.searchMovies(title, genre, minRating, startDate, endDate);

        List<MovieResponse> movieResponses = MovieMapper.fromDomainListToResponseList(movies);

        return ResponseEntity.ok(
                BaseResponse.success("Movies fetched successfully", movieResponses)
        );
    }
}