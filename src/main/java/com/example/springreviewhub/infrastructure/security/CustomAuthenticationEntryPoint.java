package com.example.springreviewhub.infrastructure.security;

import com.example.springreviewhub.adapter.presenter.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom entry point for handling unauthorized access in the application.
 * <p>
 * This class is triggered whenever an unauthenticated user tries to access
 * a secured resource. It provides a consistent error response in JSON format,
 * including a failure message and HTTP 401 (Unauthorized) status code.
 * </p>
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * Constructor to inject an ObjectMapper instance for JSON serialization.
     *
     * @param objectMapper the ObjectMapper used for converting objects to JSON
     */
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handles unauthorized access attempts by sending a JSON response.
     *
     * @param request       the incoming HTTP request
     * @param response      the HTTP response to modify
     * @param authException the exception representing the authentication failure
     * @throws IOException      if an I/O error occurs while writing the response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Set HTTP status code to 401 (Unauthorized).
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Set the response content type to JSON.
        response.setContentType("application/json");

        // Create a failure response object with the authentication failure message.
        BaseResponse<Object> baseResponse = BaseResponse.failure("authentication failed: " + authException.getMessage());

        // Serialize the response object to JSON and write it to the response body.
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}

