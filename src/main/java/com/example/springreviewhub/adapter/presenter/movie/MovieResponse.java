package com.example.springreviewhub.adapter.presenter.movie;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}
