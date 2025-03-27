package org.opensource.chatroom.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatroomRepository implements ChatroomPersistencePort {

    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public Optional<Chatroom> findById(Long id) {
        return chatroomJpaRepository.findById(id).map(ChatroomEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        chatroomJpaRepository.deleteById(id);
    }
}
