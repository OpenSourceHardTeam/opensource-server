package org.opensource.userchatroom.port.in.usecase;

import org.opensource.userchatroom.domain.UserChatroom;

import java.util.List;

public interface UserChatroomQueryUsecase {

    UserChatroom findUserInChatRoom(Long userId, Long chatRoomId);

    List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId);

    List<UserChatroom> findParticipantsInChatRoom(Long chatRoomId);
}
