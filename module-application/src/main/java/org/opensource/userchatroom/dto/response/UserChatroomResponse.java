package org.opensource.userchatroom.dto.response;

import lombok.AllArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;

@AllArgsConstructor
public class UserChatroomResponse {

    private Long userChatroomId;
    private User user;
    private Chatroom chatroom;
}
