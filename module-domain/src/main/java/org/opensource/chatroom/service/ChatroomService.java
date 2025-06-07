package org.opensource.chatroom.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.port.in.usecase.BookUsecase;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.chatroom.port.in.usecase.ChatroomUpdateUsecase;
import org.opensource.chatroom.port.in.usecase.ChatroomUsecase;

import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.opensource.chatroom.repository.ChatroomJpaRepository;
import org.opensource.chatroom.repository.ChatroomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatroomService implements ChatroomUsecase, ChatroomUpdateUsecase {

    private final ChatroomPersistencePort chatroomPersistencePort;
    private final BookUsecase bookUsecase;
    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Chatroom findById(Long chatroomId) {
        return chatroomPersistencePort
                .findById(chatroomId)
                .orElseThrow(() -> new NoSuchElementException("Chatroom with id " + chatroomId + " not found"));
    }

    @Override
    public Long createBy(CreateChatroomCommand command) {
        Book book = bookUsecase.findBookById(command.bookId());
        return chatroomPersistencePort.save(
                Chatroom.builder()
                        .topic(command.topic())
                        .book(book)
                        .build()
        );
    }

    @Override
    public void deleteById(Long chatroomId) {
        chatroomPersistencePort.deleteById(chatroomId);
    }

    @Override
    @Transactional
    public Chatroom updateTopic(Long id, String newTopic) {
        ChatroomEntity entity = chatroomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다. ID: " + id));
        entity.updateTopic(newTopic);
        return entity.toModel();
    }
}
