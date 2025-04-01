package org.opensource.userchatroom.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JoinUserInChatroomRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long chatroomId;
}
