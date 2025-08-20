package com.forohub.ForoHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration}") private long expirationMs;

    public String generarToken(String subjectEmail) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(subjectEmail)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(new Date(now.toEpochMilli() + expirationMs))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarYObtenerSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build()
                .verify(token)
                .getSubject();
    }
}
