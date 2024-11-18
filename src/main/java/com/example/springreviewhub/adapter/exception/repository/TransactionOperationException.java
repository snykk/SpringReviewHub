package com.example.springreviewhub.adapter.exception.repository;

/**
 * Custom exception for database transaction errors.
 */
public class TransactionOperationException extends RuntimeException {
    public TransactionOperationException(String message) {
        super(message);
    }

    public TransactionOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
