package com.example.springreviewhub.core.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReviewDomain {
    private Long id;
    private String text;
    private Integer rating;
    private Long movieId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //=========== Chaining Setters =============

    public ReviewDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public ReviewDomain setText(String text) {
        this.text = text;
        return this;
    }

    public ReviewDomain setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public ReviewDomain setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public ReviewDomain setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public ReviewDomain setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ReviewDomain setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
