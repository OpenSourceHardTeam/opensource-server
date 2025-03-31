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
    private List<UserChatroom> userChatrooms = new ArrayList<>();

    @Builder
    public User(
            Long id,
            String name,
            List<UserChatroom> userChatrooms) {
        this.id = id;
        this.name = name;
        this.userChatrooms = userChatrooms;
    }
}
