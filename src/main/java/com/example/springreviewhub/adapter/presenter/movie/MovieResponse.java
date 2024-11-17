package com.example.springreviewhub.adapter.presenter.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private String genre;
    private String director;
    private BigDecimal rating;
}

