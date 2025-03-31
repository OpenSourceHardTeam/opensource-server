package org.opensource.userchatroom.port.in.usecase;

import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;

import java.util.List;

public interface UserChatroomUsecase {

    Object joinUserInChatroom(JoinUserInChatroomCommand command);

    void leaveUsersAtChatRoomByChatRoomId(Long chatroomId);

    UserChatroom findUserInChatRoom(Long userId, Long chatRoomId);

    List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId);

    List<UserChatroom> findParticipantsInChatRoom(Long chatRoomId);

    Boolean doesUserExistInChatRoom(Long userId, Long chatRoomId);
}
