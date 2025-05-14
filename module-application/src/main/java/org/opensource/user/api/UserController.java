package org.opensource.user.api;

import dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.opensource.user.dto.request.SignInRequestDto;
import org.opensource.user.dto.request.SignUpRequestDto;
import org.opensource.user.dto.response.SignInResponseDto;
import org.opensource.user.dto.response.SignUpResponseDto;
import org.opensource.user.facade.UserFacade;
import org.springframework.web.bind.annotation.*;
import type.user.UserSuccessType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "유저 API Document")
public class UserController implements UserApi {
    private final UserFacade userFacade;

    @Override
    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "유저 회원가입")
    public ApiResponse<SignUpResponseDto> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "닉네임은 2~10자여야합니다" +
                    "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 5자 ~ 20자의 비밀번호여야 합니다")
            @RequestBody @Valid final SignUpRequestDto request
    ) {
        return ApiResponse.success(UserSuccessType.SIGN_UP_SUCCESS, userFacade.signUp(request.getName(), request.getEmail(), request.getPassword()));
    }

    @Override
    @PostMapping("/signin")
    @Operation(summary = "로그인 API", description = "유저 로그인")
    public ApiResponse<SignInResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto request) {
        return ApiResponse.success(UserSuccessType.SIGN_IN_SUCCESS, userFacade.signIn(request.getEmail(), request.getPassword()));
    }

    @Override
    @PostMapping("/email-exist")
    @Operation(summary = "이메일 확인 API", description = "이메일이 존재하는지 확인합니다.")
    public ApiResponse emailExist(
            @RequestParam String email
    ) {
        userFacade.checkEmailExists(email);
        return ApiResponse.success(UserSuccessType.EMAIL_CAN_USE);
    }


    @PostMapping("/name-exist")
    @Operation(summary = "이름 확인 API", description = "이름이 존재하는지 확인합니다.")
    public ApiResponse nameExist(
            @RequestParam String name
    ) {
        userFacade.checkNameExists(name);
        return ApiResponse.success(UserSuccessType.NAME_CAN_USE);
    }
}
