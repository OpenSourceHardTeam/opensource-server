package org.opensource.userchatroom.port.out.persistence;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;
import java.util.Optional;

public interface UserChatroomPersistencePort {

    Long save(UserChatroom userChatRoom);

    Optional<UserChatroom> findByUserAndChatRoom(User user, Chatroom chatRoom);

    // 특정 유저가 참여한 채팅방 목록 조회
    List<UserChatroom> findByUser(User user);

    // 특정 채팅방에 속한 유저 목록 조회
    List<UserChatroom> findByChatRoom(Chatroom chatRoom);

    // 특정 유저가 특정 채팅방에 참여 여부 확인
    boolean existsByUserAndChatRoom(User user, Chatroom chatRoom);

    void delete(UserChatroom userChatRoom);
}
