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

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (isExcludedFromAuth(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }


        String authorizationHeader = request.getHeader("Authorization");

        try {
            String token = getToken(authorizationHeader);

            if (!jwtService.validateToken(token)) {
                throw new InvalidTokenException("invalid token");
            }

            Claims claims = jwtService.extractAllClaims(token);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims, null, Collections.singleton(() -> ((String) claims.get("role")).toLowerCase())
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (InvalidAuthHeaderException | InvalidTokenException e) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"success\": %b, \"message\": \"%s\"}", false, message)
        );
    }


    private boolean isExcludedFromAuth(String uri) {
        return uri.startsWith("/api/auth/login") || uri.startsWith("/api/auth/register");
    }

    private static String getToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new InvalidAuthHeaderException("missing authorization header");
        }

        String[] headerParts = authorizationHeader.split(" ");

        if (headerParts.length != 2) {
            throw new InvalidAuthHeaderException("invalid header format");
        }

        if (!headerParts[0].equals("Bearer")) {
            throw new InvalidAuthHeaderException("token must contain 'Bearer'");
        }

        return headerParts[1];
    }
}
