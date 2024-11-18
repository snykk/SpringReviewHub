package com.example.springreviewhub.adapter.presenter.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {

    private Long id;
    private String text;
    private Integer rating;
    private Long movieId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Chaining Setters
    public ReviewResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public ReviewResponse setText(String text) {
        this.text = text;
        return this;
    }

    public ReviewResponse setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public ReviewResponse setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public ReviewResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public ReviewResponse setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ReviewResponse setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
