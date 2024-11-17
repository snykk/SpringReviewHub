package com.example.springreviewhub.core.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Long id) {
        super("Movie with ID " + id + " not found.");
    }
}
