package org.opensource.chatroom.mapper;

import org.opensource.chatroom.port.in.command.CreateChatroomCommand;

public class ChatroomMapper {

    public static CreateChatroomCommand toCommand(String topic, Long bookId) {
        return new CreateChatroomCommand(topic, bookId);
    }
}
