package org.opensource.common;

import dto.response.ApiResponse;
import exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(BaseException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ApiResponse.error(ex), ex.getHttpStatus());
    }
}
