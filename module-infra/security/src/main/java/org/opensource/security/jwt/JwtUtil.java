package org.opensource.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.port.out.SecurityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil implements SecurityPort {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder()
                .encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createToken(UserCredentials userCredentials) {
        final Date now = new Date();

        return Jwts.builder()
                .claims()
                .add("userId", userCredentials.getUser().getId())
                .and()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 10000 * 3600 * 1000L))
                .signWith(getSigningKey())
                .compact();
    }


    @Override
    public void verifyToken(String token) {
        final Claims claims = getBody(token);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        final Claims claims = getBody(token);
        Object userId = claims.get("userId");

        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        } else if (userId instanceof Long) {
            return (Long) userId;
        } else if (userId instanceof String) {
            return Long.parseLong((String) userId);
        } else {
            throw new IllegalArgumentException("Invalid type for userId in JWT");
        }
    }

    private Key getSigningKey() {
        final byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getBody(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}