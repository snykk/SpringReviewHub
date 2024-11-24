package com.example.springreviewhub.infrastructure.security;

import com.example.springreviewhub.infrastructure.exception.InvalidAuthHeaderException;
import com.example.springreviewhub.infrastructure.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT Authentication Filter for processing incoming requests.
 * <p>
 * This filter validates JWT tokens from incoming HTTP requests to ensure
 * that they are valid and properly authenticated. It intercepts each request
 * and applies security measures by setting up the security context.
 * </p>
 * <p>
 * Key responsibilities:
 * <ul>
 *   <li>Extracting and validating JWT tokens from the Authorization header.</li>
 *   <li>Authenticating the user based on token claims.</li>
 *   <li>Excluding certain endpoints (e.g., login and register) from token validation.</li>
 *   <li>Setting appropriate error responses for invalid tokens or headers.</li>
 * </ul>
 * </p>
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    /**
     * Main filter logic to validate tokens and set up authentication context.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain for continuing request processing
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (isExcludedFromAuth(request.getRequestURI())) {
            // Skip token validation for excluded endpoints.
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        try {
            String token = getToken(authorizationHeader);

            if (!jwtService.validateToken(token)) {
                throw new InvalidTokenException("Invalid token");
            }

            Claims claims = jwtService.extractAllClaims(token);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims, null, Collections.singleton(() -> ((String) claims.get("role")).toLowerCase())
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (InvalidAuthHeaderException | InvalidTokenException e) {
            // Handle invalid headers or tokens with a 401 Unauthorized response.
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors with a 500 Internal Server Error response.
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
        }
    }

    /**
     * Sets the HTTP response for error scenarios.
     *
     * @param response the HTTP response to modify
     * @param status   the HTTP status code to set
     * @param message  the error message to include in the response body
     * @throws IOException if an I/O error occurs
     */
    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"success\": %b, \"message\": \"%s\"}", false, message)
        );
    }

    /**
     * Checks if the current request URI is excluded from authentication.
     *
     * @param uri the request URI to check
     * @return true if the URI is excluded, false otherwise
     */
    private boolean isExcludedFromAuth(String uri) {
        return uri.startsWith("/api/auth/login") ||
                uri.startsWith("/api/auth/register") ||
                uri.startsWith("/api/auth/send-otp") ||
                uri.startsWith("/api/auth/verify-otp");
    }

    /**
     * Extracts the token from the Authorization header.
     *
     * @param authorizationHeader the Authorization header value
     * @return the extracted token
     * @throws InvalidAuthHeaderException if the header is missing, invalid, or not in the correct format
     */
    private static String getToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new InvalidAuthHeaderException("Missing authorization header");
        }

        String[] headerParts = authorizationHeader.split(" ");

        if (headerParts.length != 2) {
            throw new InvalidAuthHeaderException("Invalid header format");
        }

        if (!headerParts[0].equals("Bearer")) {
            throw new InvalidAuthHeaderException("Token must contain 'Bearer'");
        }

        return headerParts[1];
    }
}
