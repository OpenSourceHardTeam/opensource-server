package org.opensource.security.filter;

import exception.BadRequestException;
import exception.InternalServerException;
import exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensource.security.dto.CustomUser;
import org.opensource.security.jwt.JwtUtil;
import org.opensource.user.domain.User;
import org.opensource.user.port.out.persistence.UserPersistencePort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import type.user.UserErrorType;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserPersistencePort userPersistencePort;
    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/ws-booking-messaging".equals(path) ||
                path.startsWith("/ws-booking-messaging/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        log.info("uri: {}", uri);

        if (CorsUtils.isPreFlightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (noAuthentication(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("jwt filter called");
        String jwtToken = parseJwt(request);
        log.info("jwt token: {}", jwtToken);

        if (jwtToken == null) {
            throw new UnauthorizedException(UserErrorType.TOKEN_NOT_FOUND);
        }

        try {
            jwtUtil.verifyToken(jwtToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(UserErrorType.TOKEN_TIME_EXPIRED_ERROR);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(UserErrorType.TOKEN_MALFORMED);
        } catch (SignatureException e) {
            throw new UnauthorizedException(UserErrorType.TOKEN_SIGNATURE_INVALID);
        } catch (Exception e) {
            throw new InternalServerException(UserErrorType.TOKEN_NOT_FOUND);
        }

        Long userId = jwtUtil.getUserIdFromToken(jwtToken);
        log.info("userId: {}", userId);
        setAuthentication(userId);

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void setAuthentication(Long userId) {
        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new BadRequestException(UserErrorType.USER_NOT_EXIST));

        CustomUser customUser = new CustomUser(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean noAuthentication(String uri) {
        return uri.startsWith("/swagger-ui") ||
                uri.startsWith("/v3") ||
                uri.startsWith("/api-docs") ||
                uri.startsWith("/favicon.ico") ||
                uri.startsWith("/swagger-ui.html") ||
                uri.startsWith("/api/v1/email/") ||
                uri.startsWith("/api/v1/user/signin") ||
                uri.startsWith("/api/v1/user/signup") ||
                uri.startsWith("/api/v1/user/email-exist") ||
                uri.startsWith("/api/v1/user/name-exist") ||
                uri.startsWith("/api/v1/book") ||
                uri.startsWith("/api/v1/vote/votes");
    }
}
