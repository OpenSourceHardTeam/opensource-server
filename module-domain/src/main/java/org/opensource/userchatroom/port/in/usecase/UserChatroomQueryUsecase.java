package org.opensource.userchatroom.port.in.usecase;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;

public interface UserChatroomQueryUsecase {

    UserChatroom findUserInChatRoom(User user, Chatroom chatroom);

    List<UserChatroom> findChatRoomsUserParticipatesIn(User user);

    List<UserChatroom> findParticipantsInChatRoom(Chatroom chatroom);
}
