package com.example.springreviewhub.adapter.presenter.movie;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MovieExtendedResponse extends MovieResponse {
    private LocalDateTime deletedAt;

    public MovieExtendedResponse setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
