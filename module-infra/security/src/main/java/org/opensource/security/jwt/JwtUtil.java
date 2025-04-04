package org.opensource.security.jwt;

import exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.port.out.SecurityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import type.user.UserErrorType;

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
    public Boolean verifyToken(String token) {
        try {
            final Claims claims = getBody(token);
            return true;
        } catch (RuntimeException e) {
            if (e instanceof ExpiredJwtException) {
                throw new UnauthorizedException(UserErrorType.TOKEN_TIME_EXPIRED_ERROR);
            }
            return false;
        }
    }

    @Override
    public String getJwtContents(String token) {
        final Claims claims = getBody(token);
        return (String) claims.get("userId");
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