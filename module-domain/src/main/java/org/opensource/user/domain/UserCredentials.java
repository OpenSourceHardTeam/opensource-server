package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCredentials {
    private Long id;
    private User user;
    private String password;
    private LoginType loginType;

    @Builder
    public UserCredentials(
            Long id,
            User user,
            String password,
            LoginType loginType) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.loginType = loginType;
    }
}
