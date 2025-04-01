package org.opensource.userchatroom.port.in.usecase;

import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;

public interface UserChatroomUsecase {

    Object joinUserInChatroom(JoinUserInChatroomCommand command);

    void deleteUserAtChatRoomByChatRoomId(Long userId, Long chatroomId);

    void deleteAllUsersWhenChatRoomDeleted(Long chatroomId);

    void deleteAllChatroomsWhenUserDeleted(Long userId);

    Boolean doesUserExistInChatRoom(Long userId, Long chatRoomId);
}
