package org.opensource.chatroom.port.in.usecase;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;

public interface ChatroomUsecase {

    Chatroom findById(Long chatroomId);

    Long createBy(CreateChatroomCommand command);

    void deleteById(Long chatroomId);

    Chatroom updateTopic(Long id, String newTopic);
}
