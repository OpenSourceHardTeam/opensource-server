package org.opensource.userchatroom.repository;

import org.opensource.userchatroom.entity.UserChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatroomJpaRepository extends JpaRepository<UserChatroomEntity, Long> {
}
