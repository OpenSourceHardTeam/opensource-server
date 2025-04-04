package org.opensource.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private Long userId;
    private String accessToken;

    public static SignInResponseDto of(Long userId, String accessToken) {
        return new SignInResponseDto(userId, accessToken);
    }
}
