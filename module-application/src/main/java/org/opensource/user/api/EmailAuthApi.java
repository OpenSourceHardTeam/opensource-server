package org.opensource.user.api;

import dto.response.ApiResponse;
import org.opensource.user.dto.response.EmailAuthResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailAuthApi {
    ApiResponse<EmailAuthResponseDto> sendAuthCode(
            @RequestParam("email") String email
    );

    ApiResponse<EmailAuthResponseDto> validationEmail(
            @RequestParam("email") String email,
            @RequestParam("auth-code") String authCode
    );
}
