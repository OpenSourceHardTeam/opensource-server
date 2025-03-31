package org.opensource.userchatroom.service;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.port.in.command.CreateChatroomCommand;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.usecase.UserChatroomUsecase;
import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserChatroomService implements UserChatroomUsecase {

    private final UserChatroomPersistencePort userChatroomPersistencePort;

    @Override
    public Long joinUserInChatroom(CreateChatroomCommand command) {
        return 0L;
    }

    @Override
    public void leaveUserAtChatRoomByChatRoomId(Long chatroomId) {

    }

    @Override
    public UserChatroom findUserInChatRoom(Long userId, Long chatRoomId) {
        return null;
    }

    @Override
    public List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId) {
        return List.of();
    }

    @Override
    public List<UserChatroom> findParticipantsInChatRoom(Long chatRoomId) {
        return List.of();
    }

    @Override
    public Boolean doesUserExistInChatRoom(Long userId, Long chatRoomId) {
        return null;
    }
}
