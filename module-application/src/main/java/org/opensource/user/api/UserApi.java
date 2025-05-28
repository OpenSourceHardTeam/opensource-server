package org.opensource.user.api;

import dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.opensource.user.annotation.LoginUserEmail;
import org.opensource.user.annotation.LoginUserId;
import org.opensource.user.dto.request.SignInRequestDto;
import org.opensource.user.dto.request.SignUpRequestDto;
import org.opensource.user.dto.response.SignInResponseDto;
import org.opensource.user.dto.response.SignUpResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {
    ApiResponse<SignUpResponseDto> signUp(
            @Valid @RequestBody SignUpRequestDto request
    );

    ApiResponse<SignInResponseDto> signIn(
            @Valid @RequestBody SignInRequestDto request
    );

    ApiResponse emailExist(
            @RequestParam String email
    );

    ApiResponse nameExist(
            @RequestParam String name
    );

    ApiResponse changeUserName(
            @LoginUserId Long userId,
            @RequestParam String newName
    );

    ApiResponse changePassword(
            @LoginUserEmail String email,
            @RequestParam String newPassword
    );
}
