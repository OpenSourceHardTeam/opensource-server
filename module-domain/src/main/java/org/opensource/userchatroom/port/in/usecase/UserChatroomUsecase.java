package org.opensource.userchatroom.port.in.usecase;

import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;

public interface UserChatroomUsecase {

    Long joinUserInChatroom(CreateChatroomCommand command);

    void leaveUserAtChatRoomByChatRoomId(Long chatroomId);

    UserChatroom findUserInChatRoom(Long userId, Long chatRoomId);

    List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId);

    List<UserChatroom> findParticipantsInChatRoom(Long chatRoomId);

    Boolean doesUserExistInChatRoom(Long userId, Long chatRoomId);
}
