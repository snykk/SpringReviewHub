package com.example.springreviewhub.adapter.exception;

import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.example.springreviewhub.core.exception.*;
import com.example.springreviewhub.infrastructure.exception.InvalidAuthHeaderException;
import com.example.springreviewhub.infrastructure.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.failure("binding exception", errors));
    }

    @ExceptionHandler(InvalidAuthHeaderException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidAuthHeaderException(InvalidAuthHeaderException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateReviewException(DuplicateReviewException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateReviewException(ReviewNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateReviewException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateReviewException(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    public ResponseEntity<BaseResponse<Object>> handleDuplicateReviewException(InvalidOldPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.failure(ex.getMessage()));

    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<BaseResponse<Object>> handleAccountLocked(AccountLockedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<BaseResponse<Object>> handleEmailNotVerified(EmailNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(PermissionIssueException.class)
    public ResponseEntity<BaseResponse<Object>> handleEmailNotVerified(PermissionIssueException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.failure("an unexpected error occurred: " + ex.getMessage()));
    }
}
