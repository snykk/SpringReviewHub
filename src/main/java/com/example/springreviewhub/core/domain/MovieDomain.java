package com.example.springreviewhub.core.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MovieDomain {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private BigDecimal rating;
    private Integer duration; // in minutes
    private String genre;
    private String director;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    //=========== Chaining Setters =============
    public MovieDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieDomain setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieDomain setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieDomain setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public MovieDomain setRating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    public MovieDomain setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public MovieDomain setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public MovieDomain setDirector(String director) {
        this.director = director;
        return this;
    }

    public MovieDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MovieDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
