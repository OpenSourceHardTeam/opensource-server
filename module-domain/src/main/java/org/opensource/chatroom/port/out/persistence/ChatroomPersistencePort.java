package org.opensource.chatroom.port.out.persistence;

import org.opensource.chatroom.domain.Chatroom;

import java.util.Optional;

public interface ChatroomPersistencePort {

    Optional<Chatroom> findById(Long id);
    void deleteById(Long id);
}
