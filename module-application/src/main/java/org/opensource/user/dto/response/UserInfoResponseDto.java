package org.opensource.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opensource.user.domain.User;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    String name;
    String email;

    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(user.getName(), user.getEmail());
    }
}
