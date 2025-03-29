package org.opensource.chatroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatroomResponse {

    private Long id;
    private String topic;
    private Long bookId;
//    private Long bookArgumentId;
}
