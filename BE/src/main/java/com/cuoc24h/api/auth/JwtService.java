package com.cuoc24h.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

/** Issues and validates admin JWTs (HS256). */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(JwtProperties properties) {
        byte[] secretBytes = properties.secret().getBytes(StandardCharsets.UTF_8);
        // Keys.hmacShaKeyFor requires >= 32 bytes for HS256.
        this.key = Keys.hmacShaKeyFor(secretBytes);
        this.expirationMinutes = properties.expirationMinutes();
    }

    public String generateToken(AdminUser admin) {
        Instant now = Instant.now();
        Instant expiry = now.plus(expirationMinutes, ChronoUnit.MINUTES);
        return Jwts.builder()
                .subject(String.valueOf(admin.getId()))
                .claim("username", admin.getUsername())
                .claim("displayName", admin.getDisplayName())
                .claim("role", admin.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();
    }

    /** Parses and validates the token, returning its claims. Throws on any problem. */
    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
