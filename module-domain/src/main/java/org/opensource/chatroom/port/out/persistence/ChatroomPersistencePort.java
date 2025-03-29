package org.opensource.chatroom.port.out.persistence;

import org.opensource.chatroom.domain.Chatroom;

import java.util.Optional;

public interface ChatroomPersistencePort {

    Long save(Chatroom chatroom);
    Optional<Chatroom> findById(Long id);
    void deleteById(Long id);
}
