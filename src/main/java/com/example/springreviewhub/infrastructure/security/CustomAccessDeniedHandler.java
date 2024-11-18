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

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper; // Inject ObjectMapper

    // Constructor injection for ObjectMapper
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Set status code to FORBIDDEN
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Set the Content-Type header to application/json
        response.setContentType("application/json");

        // Create BaseResponse object with failure message
        BaseResponse<Object> baseResponse = BaseResponse.failure("Insufficient permission: you don't have access to this resource");

        // Write the JSON response
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}
