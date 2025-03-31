package org.opensource.userchatroom.port.out.persistence;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;
import java.util.Optional;

public interface UserChatroomPersistencePort {

    Long save(UserChatroom userChatroom);

    Optional<UserChatroom> findByUserIdAndChatroomId(Long userId, Long chatroomId);

    List<UserChatroom> findChatroomListByUserId(Long userId);

    List<UserChatroom> findUserListByChatRoomId(Long chatroomId);

    boolean existsByUserIdAndChatRoomId(Long userId, Long chatroomId);

    void deleteById(Long id);
}
