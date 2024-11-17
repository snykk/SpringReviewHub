package com.example.springreviewhub.adapter.presenter.movie;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovieRequest {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String genre;
    private String director;
    private BigDecimal rating;
}
