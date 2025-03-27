package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCredentials {
    private Long id;
    private User user;
    private String password;
    private LoginType loginType;
    private String refreshToken;

    @Builder
    public UserCredentials(
            long id,
            User user,
            String password,
            LoginType loginType,
            String refreshToken) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.loginType = loginType;
        this.refreshToken = refreshToken;
    }
}
