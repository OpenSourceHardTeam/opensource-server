package org.opensource.chatroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatroomResponse {

    private String topic;
    private Long bookId;
}
