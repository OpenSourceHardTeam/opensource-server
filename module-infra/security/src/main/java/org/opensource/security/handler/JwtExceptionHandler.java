package org.opensource.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensource.security.jwt.dto.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandler extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(e.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse res =
                    new ErrorResponse(
                            e.getHttpStatus().value(),
                            e.getErrorType().getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(res));
        }
    }
}
