package org.opensource.chatroom.mapper;

import org.opensource.chatroom.port.in.command.CreateChatroomCommand;

public class ChatroomMapper {

    public static CreateChatroomCommand toCommand(String topic, Long bookId, Long bookArgumentId) {
        return new CreateChatroomCommand(topic, bookId, bookArgumentId);
    }
}
