package com.example.springreviewhub.infrastructure.security;

import com.example.springreviewhub.infrastructure.exception.InvalidAuthHeaderException;
import com.example.springreviewhub.infrastructure.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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
        String token = getToken(authorizationHeader);

        try {
            if (jwtService.validateToken(token)) {
                Claims claims = jwtService.extractAllClaims(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        claims, null, Collections.singleton(() -> ((String) claims.get("role")).toLowerCase()) // Menambahkan role
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidAuthHeaderException | InvalidTokenException e) {
            // Melempar exception untuk penanganan di filter chain berikutnya
            throw e;
        } catch (Exception e) {
            // Melempar generic exception untuk penanganan default
            throw new InvalidTokenException("Unauthorized: Token is invalid or expired");
        }

        chain.doFilter(request, response);
    }

    private boolean isExcludedFromAuth(String uri) {
        return uri.startsWith("/api/auth/login") || uri.startsWith("/api/auth/register");
    }

    private static String getToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new InvalidAuthHeaderException("Missing Authorization Header");
        }

        String[] headerParts = authorizationHeader.split(" ");

        if (headerParts.length != 2) {
            throw new InvalidAuthHeaderException("Invalid Header Format");
        }

        if (!headerParts[0].equals("Bearer")) {
            throw new InvalidAuthHeaderException("Token must contain Bearer");
        }

        return headerParts[1];
    }
}
