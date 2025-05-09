package org.opensource.common;

import dto.response.ApiResponse;
import exception.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.opensource.book.api.BookController;
import org.opensource.chatroom.api.ChatroomApiController;
import org.opensource.user.api.EmailAuthController;
import org.opensource.user.api.UserController;
import org.opensource.userchatroom.api.UserChatroomApiController;
import org.opensource.vote.api.VoteController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Hidden
public class GlobalExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(BaseException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ApiResponse.error(ex), ex.getHttpStatus());
    }
}
