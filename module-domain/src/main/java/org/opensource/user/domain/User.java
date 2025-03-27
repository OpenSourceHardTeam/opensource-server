package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private long id;
    private String userName;

    @Builder
    public User(
            long id,
            String userName) {
        this.id = id;
        this.userName = userName;
    }
}
