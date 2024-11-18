package com.example.springreviewhub.core.exception;

public class ConflictUsernameException extends RuntimeException {
    public ConflictUsernameException(String message) {
        super(message);
    }
}
