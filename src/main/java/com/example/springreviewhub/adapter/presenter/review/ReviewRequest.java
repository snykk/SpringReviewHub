package com.example.springreviewhub.adapter.presenter.review;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank(message = "Review text is required")
    @Size(min = 10, message = "Review text must be at least 10 characters")
    private String text;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must not exceed 10")
    private Integer rating;

    @NotNull(message = "Movie ID is required")
    private Long movieId;
}
