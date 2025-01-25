package com.rajesh.practice.identify_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    //Here we are extracting all the information contain inside the token as a claim
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()  // Use parserBuilder instead of parser
                .setSigningKey(getSigningKey())  // Set the signing key here
                .build()  // Build the parser
                .parseClaimsJws(token)  // Parse the claims
                .getBody();  // Extract the claims body
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set the claims
                .setSubject(subject) // Set the subject
                .setHeaderParam("typ", "JWT") // Set the header parameter (type)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set expiration time (1 hour)
                .signWith(getSigningKey()) // Sign the JWT using the signing key
                .compact(); // Build the token
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
