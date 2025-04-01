package org.opensource.user.api;

import dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.opensource.user.dto.request.SignUpRequestDto;
import org.opensource.user.dto.response.SignUpResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {
    ApiResponse<SignUpResponseDto> signUp(
            @Valid @RequestBody SignUpRequestDto request
    );

    ApiResponse emailExist(
            @RequestParam String email
    );

    ApiResponse nameExist(
            @RequestParam String name
    );
}
