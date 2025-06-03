package org.opensource.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeUserInfoRequestDto {
    private String newName;
    private String newPassword;
}
