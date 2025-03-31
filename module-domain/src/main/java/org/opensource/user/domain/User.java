package org.opensource.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private Long id;
    private String name;

    @Builder
    public User(
            Long id,
            String name) {
        this.id = id;
        this.name = name;
    }
}
