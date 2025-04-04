package org.opensource.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDto {
    @Email(message = "이메일 형식에 맞지 않습니다")
    @NotBlank
    private String email;

    @NotNull
    private String password;
}
