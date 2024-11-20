package com.example.springreviewhub.adapter.controller;

import com.example.springreviewhub.adapter.mapper.MovieMapper;
import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.adapter.presenter.movie.MovieResponse;
import com.example.springreviewhub.core.domain.MovieDomain;
import com.example.springreviewhub.core.interfaces.usecases.IMovieUseCase;
import com.example.springreviewhub.adapter.presenter.movie.MovieRequest;
import com.example.springreviewhub.infrastructure.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<BaseResponse<List<MovieResponse>>> getAllMovies(
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);
        List<MovieDomain> movies = movieUseCase.getAllMoviesWithRole(role);

        List<MovieResponse> responses = MovieMapper.fromDomainListToResponseList(movies, role);

        return ResponseEntity.ok(BaseResponse.success(
                "movies data fetched successfully",
                responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponse>> getMovieById(
            @PathVariable Long id,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        MovieDomain movie = movieUseCase.getMovieByIdWithRole(id, role);

        MovieResponse movieResponse = MovieMapper.fromDomainToMovieResponse(movie, role);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("Movie data with id %d fetched successfully", id),
                movieResponse));

    }

    @PostMapping
    public ResponseEntity<BaseResponse<MovieResponse>> createMovie(
            @RequestBody @Valid MovieRequest movieReq,
            @AuthenticationPrincipal Claims claims
            ) {
        String role = JwtService.extractRoleFromClaims(claims);

        MovieDomain movieDomain = MovieMapper.fromMovieRequestToDomain(movieReq);

        MovieDomain createdMovie = movieUseCase.createMovie(movieDomain);

        return ResponseEntity.status(201).body(BaseResponse.success(
                "movie data create successfully",
                MovieMapper.fromDomainToMovieResponse(createdMovie, role))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponse>> updateMovie(
            @PathVariable Long id,
            @RequestBody @Valid MovieRequest movieReq,
            @AuthenticationPrincipal Claims claims
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        MovieDomain movieDomain = MovieMapper.fromMovieRequestToDomain(movieReq);

        MovieDomain updatedMovie = movieUseCase.updateMovie(id, movieDomain);

        return ResponseEntity.ok(BaseResponse.success(
                String.format("Movie with id %d has been successfully updated", id),
                MovieMapper.fromDomainToMovieResponse(updatedMovie, role)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable Long id
    ) {
        movieUseCase.deleteMovie(id);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<MovieResponse>>> searchMovies(
            @AuthenticationPrincipal Claims claims,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        String role = JwtService.extractRoleFromClaims(claims);

        if (minRating != null && (minRating.compareTo(BigDecimal.ONE) < 0 || minRating.compareTo(BigDecimal.TEN) > 0)) {
            throw new IllegalArgumentException("minRating must be between 1.0 and 10.0");
        }

        List<MovieDomain> movies = movieUseCase.searchMovies(title, genre, minRating, startDate, endDate);

        List<MovieResponse> movieResponses = MovieMapper.fromDomainListToResponseList(movies, role);

        return ResponseEntity.ok(
                BaseResponse.success("Movies fetched successfully", movieResponses)
        );
    }
}
