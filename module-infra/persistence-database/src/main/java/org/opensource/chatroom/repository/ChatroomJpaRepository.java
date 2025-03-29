package org.opensource.chatroom.repository;

import org.opensource.chatroom.entity.ChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomEntity, Long> {
}
