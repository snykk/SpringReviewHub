package com.example.springreviewhub.infrastructure.security;

import com.example.springreviewhub.core.domain.UserDomain;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing JSON Web Tokens (JWTs).
 * <p>
 * This class provides functionalities for generating, validating, and parsing JWT tokens.
 * It uses the HMAC-SHA256 algorithm for signing the tokens, ensuring secure and tamper-proof
 * authentication. The secret key and token expiration time are configurable through application properties.
 * </p>
 * <p>
 * Primary responsibilities of this class include:
 * <ul>
 *   <li>Creating tokens with custom claims</li>
 *   <li>Validating tokens for integrity and expiration</li>
 *   <li>Extracting information from tokens, such as the username and additional claims</li>
 * </ul>
 * </p>
 */
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    /**
     * Retrieves the signing key used for JWT creation and validation.
     * <p>
     * The key is generated using the secret key defined in the application's configuration.
     *
     * @return a {@link Key} object for HMAC-SHA encryption
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Generates a JWT token for the specified user.
     * <p>
     * The token includes custom claims, such as the user's ID, email, and role, in addition
     * to the subject (username) and expiration time. The token is signed using the HS256 algorithm.
     *
     * @param username the subject of the token (typically the username)
     * @param user     a {@link UserDomain} object representing the authenticated user
     * @return a signed JWT token as a {@link String}
     */
    public String generateToken(String username, UserDomain user) {
        // Define custom claims to be included in the JWT
        Map<String, Object> extraClaims = new HashMap<>() {{
            put("id", user.getId());
            put("email", user.getEmail());
            put("role", user.getRole());
        }};

        // Build and sign the token
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the provided JWT token.
     * <p>
     * The method checks if the token is valid and properly signed using the configured signing key.
     * If the token is invalid or expired, the method catches the exception and returns false.
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token) {
        try {
            // Parse and validate the token
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Handle invalid or expired token
            return false;
        }
    }

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token from which to extract the subject
     * @return the username (subject) as a {@link String}
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts all claims from the provided JWT token.
     * <p>
     * This method parses the token and retrieves its claims, such as subject, expiration,
     * and custom claims defined during token generation.
     *
     * @param token the JWT token from which to extract claims
     * @return a {@link Claims} object containing all claims from the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
