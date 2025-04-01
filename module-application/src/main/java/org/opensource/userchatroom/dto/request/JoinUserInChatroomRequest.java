package org.opensource.userchatroom.dto.request;

import lombok.Getter;

@Getter
public class JoinUserInChatroomRequest {

    private Long userId;

    private Long chatroomId;
}
