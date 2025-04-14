package com.example.utils;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "your-secret-key";  // Change this to a secure key
    private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    // Ensure that the parameter is of type Authentication
    public String generateJwtToken(Authentication authentication) {
        // Get username (or other user info) from the Authentication object
        String username = authentication.getName();

        // Generate and return JWT token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
