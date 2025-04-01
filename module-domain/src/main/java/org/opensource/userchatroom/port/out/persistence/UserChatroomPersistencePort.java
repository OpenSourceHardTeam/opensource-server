package org.opensource.userchatroom.port.out.persistence;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;
import java.util.Optional;

public interface UserChatroomPersistencePort {

    Long save(UserChatroom userChatroom);

    Optional<UserChatroom> findByUserIdAndChatroomId(User user, Chatroom chatroom);

    List<UserChatroom> findChatroomListByUserId(User user);

    List<UserChatroom> findUserListByChatRoomId(Chatroom chatroom);

    boolean existsByUserIdAndChatRoomId(User user, Chatroom chatroom);

    void deleteById(Long id);
}
