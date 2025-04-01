package org.opensource.user.api;

import dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.opensource.user.dto.request.SignUpRequestDto;
import org.opensource.user.dto.response.SignUpResponseDto;
import org.opensource.user.facade.UserFacade;
import org.springframework.web.bind.annotation.*;
import type.user.UserSuccessType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {
    private final UserFacade userFacade;

    @PostMapping("/signup")
    public ApiResponse<SignUpResponseDto> signUp(
            @RequestBody @Valid final SignUpRequestDto request
    ) {
        return ApiResponse.success(UserSuccessType.SIGN_UP_SUCCESS, userFacade.signUp(request.getName(), request.getEmail(), request.getPassword()));
    }

    @PostMapping("/email-exist")
    public ApiResponse emailExist(
            @RequestParam String email
    ) {
        userFacade.checkEmailExists(email);
        return ApiResponse.success(UserSuccessType.EMAIL_CAN_USE);
    }

    @PostMapping("/name-exist")
    public ApiResponse nameExist(
            @RequestParam String name
    ) {
        userFacade.checkNameExists(name);
        return ApiResponse.success(UserSuccessType.NAME_CAN_USE);
    }
}
