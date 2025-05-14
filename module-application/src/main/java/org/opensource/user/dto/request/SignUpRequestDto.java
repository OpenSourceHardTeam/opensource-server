package org.opensource.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
            message = "닉네임은 2~10자여야합니다")
    private String name;

    @NotBlank
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotNull
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{5,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 5자 ~ 20자의 비밀번호여야 합니다"
    )
    private String password;
}
