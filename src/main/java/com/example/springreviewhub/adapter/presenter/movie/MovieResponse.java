package com.example.springreviewhub.adapter.presenter.movie;

import com.example.springreviewhub.adapter.presenter.review.ReviewResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String genre;
    private String director;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReviewResponse> reviews;

    // Chaining setters
    public MovieResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieResponse setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public MovieResponse setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public MovieResponse setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public MovieResponse setDirector(String director) {
        this.director = director;
        return this;
    }

    public MovieResponse setRating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    public MovieResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MovieResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MovieResponse setReviews(List<ReviewResponse> reviews) {
        this.reviews = reviews;
        return this;
    }
}
