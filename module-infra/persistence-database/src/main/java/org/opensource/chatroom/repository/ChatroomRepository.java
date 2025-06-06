package org.opensource.chatroom.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.book.entity.BookEntity;
import org.opensource.book.repository.BookJpaRepository;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatroomRepository implements ChatroomPersistencePort {

    private final ChatroomJpaRepository chatroomJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    @Override
    public Long save(Chatroom chatroom) {
        BookEntity bookEntity = bookJpaRepository.findById(chatroom.getBook().getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        ChatroomEntity entity = ChatroomEntity.builder()
                .topic(chatroom.getTopic())
                .book(bookEntity)
                .build();

        return chatroomJpaRepository.save(entity).getId();
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        return chatroomJpaRepository.findById(id).map(ChatroomEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        chatroomJpaRepository.deleteById(id);
    }
}
