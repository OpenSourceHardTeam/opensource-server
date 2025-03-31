package org.opensource.userchatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;

@Getter
public class UserChatroom {

    private Long id;
    private User user;
    private Chatroom chatroom;
    private Boolean isOnline;

    @Builder
    public UserChatroom(
            Long id,
            User user,
            Chatroom chatroom,
            Boolean isOnline) {
        this.id = id;
        this.user = user;
        this.chatroom = chatroom;
        this.isOnline = isOnline;
    }
}

