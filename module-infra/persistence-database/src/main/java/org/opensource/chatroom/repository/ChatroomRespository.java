package org.opensource.chatroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.chatroom.port.out.persistence.ChatroomPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatroomRespository implements ChatroomPersistencePort {

    @PersistenceContext
    private EntityManager em;

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
