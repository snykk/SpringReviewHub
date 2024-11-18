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

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Set status code to FORBIDDEN
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Set the Content-Type header to application/json
        response.setContentType("application/json");

        // Create BaseResponse object with failure message
        BaseResponse<Object> baseResponse = BaseResponse.failure("unauthorized: Token is invalid or expired");

        // Write the JSON response
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}

