package com.example.springreviewhub.infrastructure.security;

import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom handler for access denied scenarios in the application.
 * <p>
 * This class is triggered whenever an authenticated user attempts to access
 * a resource they do not have sufficient permissions for. It returns a JSON
 * response with a failure message and HTTP 403 (Forbidden) status code.
 * </p>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    /**
     * Constructor to inject an ObjectMapper instance for JSON serialization.
     *
     * @param objectMapper the ObjectMapper used for converting objects to JSON
     */
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handles access denied exceptions by sending a JSON response.
     *
     * @param request               the incoming HTTP request
     * @param response              the HTTP response to modify
     * @param accessDeniedException the exception representing the access denial
     * @throws IOException if an I/O error occurs while writing the response
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Set HTTP status code to 403 (Forbidden).
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Set the response content type to JSON.
        response.setContentType("application/json");

        // Create a failure response object with the access denial message.
        BaseResponse<Object> baseResponse = BaseResponse.failure("forbidden: you don't have access to this resource");

        // Serialize the response object to JSON and write it to the response body.
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}
