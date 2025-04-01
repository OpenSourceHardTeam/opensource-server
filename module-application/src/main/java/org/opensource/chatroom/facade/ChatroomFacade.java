package org.opensource.chatroom.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.dto.request.CreateChatroomRequest;
import org.opensource.chatroom.dto.response.CreateChatroomResponse;
import org.opensource.chatroom.mapper.ChatroomMapper;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.chatroom.port.in.usecase.ChatroomUpdateUsecase;
import org.opensource.chatroom.port.in.usecase.ChatroomUsecase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatroomFacade {

    private final ChatroomUsecase chatroomUsecase;
    private final ChatroomUpdateUsecase chatroomUpdateUsecase;

    public Chatroom findChatroomById(Long id) {
        return chatroomUsecase.findById(id);
    }

    public Long createChatroom(CreateChatroomRequest request) {
        return chatroomUsecase.createBy(ChatroomMapper.toCommand(
                request.getTopic(), request.getBookId(), request.getBookArgumentId()
        ));
    }

    public void deleteChatroom(Long id) {
        chatroomUsecase.deleteById(id);
    }
}
