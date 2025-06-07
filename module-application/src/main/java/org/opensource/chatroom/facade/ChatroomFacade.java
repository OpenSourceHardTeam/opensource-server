package org.opensource.chatroom.facade;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.port.in.usecase.BookUsecase;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.dto.request.CreateChatroomRequest;
import org.opensource.chatroom.dto.response.CreateChatroomResponse;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.mapper.ChatroomMapper;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.chatroom.port.in.usecase.ChatroomUpdateUsecase;
import org.opensource.chatroom.port.in.usecase.ChatroomUsecase;
import org.opensource.chatroom.repository.ChatroomJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatroomFacade {

    private final ChatroomUsecase chatroomUsecase;
    private final ChatroomJpaRepository chatroomJpaRepository;

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

    // 새로 추가: 직접 구현
    @Transactional
    public Chatroom updateChatroomTopic(Long id, String newTopic) {
        ChatroomEntity entity = chatroomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다. ID: " + id));

        // 엔티티 직접 수정 (더티 체킹으로 자동 UPDATE)
        entity.updateTopic(newTopic);

        // 수정된 엔티티를 도메인 객체로 변환하여 반환
        return entity.toModel();
    }
}
