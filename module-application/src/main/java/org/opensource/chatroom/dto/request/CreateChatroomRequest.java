package org.opensource.chatroom.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateChatroomRequest {

    @NotNull
    private String topic;

    @NotNull
    private Long bookId;
}
