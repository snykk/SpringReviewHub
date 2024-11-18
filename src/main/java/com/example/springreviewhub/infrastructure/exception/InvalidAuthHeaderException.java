package com.example.springreviewhub.infrastructure.exception;

public class InvalidAuthHeaderException extends RuntimeException {
    public InvalidAuthHeaderException(String message) {
        super(message);
    }
}
