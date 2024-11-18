package com.example.springreviewhub.core.exception;

public class InsufficientPermission extends RuntimeException {
    public InsufficientPermission(String message) {
        super(message);
    }
}
