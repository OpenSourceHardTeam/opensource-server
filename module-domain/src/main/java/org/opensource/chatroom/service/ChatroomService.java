package org.opensource.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.chatroom.port.in.usecase.ChatroomUpdateUsecase;
import org.opensource.chatroom.port.in.usecase.ChatroomUsecase;

import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatroomService implements ChatroomUsecase, ChatroomUpdateUsecase {

    private final ChatroomPersistencePort chatroomPersistencePort;

    @Override
    @Transactional(readOnly = true)
    public Chatroom findById(Long chatroomId) {
        return chatroomPersistencePort
                .findById(chatroomId)
                .orElseThrow(() -> new NoSuchElementException("Chatroom with id " + chatroomId + " not found"));
    }

    @Override
    public Long createBy(CreateChatroomCommand command) {
        return chatroomPersistencePort.save(
                Chatroom.builder()
                        .topic(command.topic())
                        .book(Book.builder().bookId(command.bookId()).build())
                        .build()
        );
    }

    @Override
    public void deleteById(Long chatroomId) {
        chatroomPersistencePort.deleteById(chatroomId);
    }
}
