package org.opensource.chatroom.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.port.in.usecase.BookUsecase;
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
@Transactional
public class ChatroomFacade {

    private final ChatroomUsecase chatroomUsecase;

    public Chatroom findChatroomById(Long id) {
        return chatroomUsecase.findById(id);
    }

    public Long createChatroom(CreateChatroomRequest request) {
        return chatroomUsecase.createBy(ChatroomMapper.toCommand(
                request.getTopic(), request.getBookId()
        ));
    }

    public void deleteChatroom(Long id) {
        chatroomUsecase.deleteById(id);
    }

    public Chatroom updateChatroomTopic(Long id, String newTopic) {
        return chatroomUsecase.updateTopic(id, newTopic);
    }
}
