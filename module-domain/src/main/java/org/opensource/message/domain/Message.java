package org.opensource.message.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.chatroom.domain.Chatroom;

@Getter
public class Message {

    private Long id;
    private Chatroom chatroom;
//    private User user;
    private String content;
    private String externalMessageId;
    private Long serverNodeId;

    @Builder
    public Message(
            Long id,
            Chatroom chatroom,
            String content,
            String externalMessageId,
            Long serverNodeId) {
        this.id = id;
        this.chatroom = chatroom;
        this.content = content;
        this.externalMessageId = externalMessageId;
        this.serverNodeId = serverNodeId;
    }
}
