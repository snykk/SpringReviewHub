package com.example.springreviewhub.adapter.presenter.review;

import com.example.springreviewhub.adapter.presenter.movie.MovieExtendedResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewExtendedResponse extends ReviewResponse {
    private LocalDateTime deletedAt;

    public ReviewExtendedResponse setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
