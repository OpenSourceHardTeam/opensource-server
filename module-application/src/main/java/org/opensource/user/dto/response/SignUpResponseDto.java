package org.opensource.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opensource.user.domain.User;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
    Long id;
    String name;
    String email;

    public static SignUpResponseDto of(User user) {
        return new SignUpResponseDto(user.getId(), user.getName(), user.getEmail());
    }
}
