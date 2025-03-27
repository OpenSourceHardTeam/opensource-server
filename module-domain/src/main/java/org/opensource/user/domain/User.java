package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private long id;
    private String name;

    @Builder
    public User(
            long id,
            String name) {
        this.id = id;
        this.name = name;
    }
}
