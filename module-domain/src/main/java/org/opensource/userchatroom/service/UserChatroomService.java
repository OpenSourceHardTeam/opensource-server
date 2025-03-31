package org.opensource.userchatroom.service;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;
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
    public Long joinUserInChatroom(JoinUserInChatroomCommand command) {
        User user = User.builder()
                .id(command.userId()).build();
        Chatroom chatroom = Chatroom.builder()
                .id(command.chatroomId()).build();
        UserChatroom userChatroom = UserChatroom.builder()
                .user(user)
                .chatroom(chatroom).build();

        return userChatroomPersistencePort.save(userChatroom);
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
