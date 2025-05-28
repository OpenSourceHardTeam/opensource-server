package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String email;
    private String name;

    @Builder
    public User(
            Long id,
            String email,
            String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public void changeUserName(String newName) {
        this.name = newName;
    }
}
