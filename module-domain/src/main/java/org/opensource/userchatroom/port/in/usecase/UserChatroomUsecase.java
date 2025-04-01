package org.opensource.userchatroom.port.in.usecase;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;

public interface UserChatroomUsecase {

    Long joinUserInChatroom(JoinUserInChatroomCommand command);

    void deleteUserAtChatRoomByChatRoomId(User user, Chatroom chatroom);

    void deleteAllUsersWhenChatRoomDeleted(Chatroom chatroom);

    void deleteAllChatroomsWhenUserDeleted(User user);

    Boolean doesUserExistInChatRoom(User user, Chatroom chatroom);
}
