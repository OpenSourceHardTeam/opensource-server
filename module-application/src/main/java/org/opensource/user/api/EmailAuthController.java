package org.opensource.user.api;

import dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Email", description = "이메일 API Document")
public class EmailAuthController implements EmailAuthApi {
    private final UserFacade userFacade;

    @PostMapping("/send-email")
    @Operation(summary = "이메일 전송 API", description = "이메일 확인을 위해 입력한 이메일로 인증번호를 전송합니다.")
    public ApiResponse<EmailAuthResponseDto> sendAuthCode(@RequestParam("email") String email) {
        userFacade.sendEmail(email);
        return ApiResponse.success(UserSuccessType.SEND_EMAIL_SUCCESS);
    }

    @PostMapping("/validation-email")
    @Operation(summary = "이메일 인증 API", description = "인증번호를 정확히 입력했는지 확인합니다.")
    public ApiResponse<EmailAuthResponseDto> validationEmail(
            @RequestParam("email") String email,
            @RequestParam("auth-code") String authCode
    ) {
        userFacade.validateEmail(email, authCode);
        return ApiResponse.success(UserSuccessType.CHECK_EMAIL_VALIDATION_SUCCESS);
    }
}
