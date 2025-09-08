package com.example.eval.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Emision y validacion de JWT (HS256).
 * - Lee secreto y expiracion desde application.yml
 *   security.jwt.secret, security.jwt.expiration-minutes
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // El secreto debe ser >= 32 bytes para HS256
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** Genera un token con subject = username */
    public String generateToken(String subject) {
        long now = System.currentTimeMillis();
        long expMillis = now + (expirationMinutes * 60_000);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Valida la firma y devuelve el subject (username) */
    public String validateAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
