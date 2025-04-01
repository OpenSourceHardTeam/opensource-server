package org.opensource.user.api;

import dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.opensource.user.dto.response.EmailAuthResponseDto;
import org.opensource.user.facade.UserFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import type.user.UserSuccessType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailAuthController implements EmailAuthApi {
    private final UserFacade userFacade;

    @PostMapping("/send-email")
    public ApiResponse<EmailAuthResponseDto> sendAuthCode(@RequestParam("email") String email) {
        userFacade.sendEmail(email);
        return ApiResponse.success(UserSuccessType.SEND_EMAIL_SUCCESS);
    }

    @PostMapping("/validation-email")
    public ApiResponse<EmailAuthResponseDto> validationEmail(
            @RequestParam("email") String email,
            @RequestParam("auth-code") String authCode
    ) {
        userFacade.validateEmail(email, authCode);
        return ApiResponse.success(UserSuccessType.CHECK_EMAIL_VALIDATION_SUCCESS);
    }
}
