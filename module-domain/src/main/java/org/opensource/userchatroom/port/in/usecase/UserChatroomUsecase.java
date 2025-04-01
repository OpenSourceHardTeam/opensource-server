package org.opensource.userchatroom.port.in.usecase;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;

public interface UserChatroomUsecase {

    Long joinUserInChatroom(User user, Chatroom chatroom, Boolean isOnline);

    void deleteUserAtChatRoomByChatRoomId(User user, Chatroom chatroom);

    void deleteAllUsersWhenChatRoomDeleted(Chatroom chatroom);

    void deleteAllChatRoomsWhenUserDeleted(User user);

    Boolean doesUserExistInChatRoom(User user, Chatroom chatroom);
}
