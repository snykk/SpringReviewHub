package com.example.springreviewhub.adapter.exception;

import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.core.exception.AuthenticationException;
import com.example.springreviewhub.core.exception.UserNotFoundException;
import com.example.springreviewhub.core.exception.UsernameAlreadyTakenException;
import com.example.springreviewhub.core.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleMovieNotFoundException(MovieNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<BaseResponse<Object>> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.failure("An unexpected error occurred: " + ex.getMessage()));
    }
}
