package org.opensource.userchatroom.service;

import lombok.RequiredArgsConstructor;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.command.JoinUserInChatroomCommand;
import org.opensource.userchatroom.port.in.usecase.UserChatroomQueryUsecase;
import org.opensource.userchatroom.port.in.usecase.UserChatroomUsecase;
import org.opensource.userchatroom.port.out.persistence.UserChatroomPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserChatroomService implements UserChatroomUsecase, UserChatroomQueryUsecase {

    private final UserChatroomPersistencePort userChatroomPersistencePort;

    @Override
    public Long joinUserInChatroom(JoinUserInChatroomCommand command) {
        return userChatroomPersistencePort
                .findByUserIdAndChatroomId(command.user(), command.chatroom())
                .map(UserChatroom::getId)
                .orElseGet(
                        () -> {
                            UserChatroom userChatroom = UserChatroom.builder()
                                            .user(command.user())
                                            .chatroom(command.chatroom())
                                            .isOnline(command.isOnline())
                                            .build();
                            return userChatroomPersistencePort.save(userChatroom);
                        }
                );
    }

    @Override
    public void deleteUserAtChatRoomByChatRoomId(User user, Chatroom chatroom) {
        UserChatroom userChatroom = userChatroomPersistencePort.findByUserIdAndChatroomId(user, chatroom).orElseThrow();
        userChatroomPersistencePort.deleteById(userChatroom.getId());
    }

    @Override
    public void deleteAllUsersWhenChatRoomDeleted(Chatroom chatroom) {
        List<UserChatroom> userChatRooms = userChatroomPersistencePort.findUserListByChatRoomId(chatroom);

        for (UserChatroom userChatroom : userChatRooms) {
            userChatroomPersistencePort.deleteById(userChatroom.getId());
        }
    }

    @Override
    public void deleteAllChatroomsWhenUserDeleted(User user) {
        List<UserChatroom> userChatRooms = userChatroomPersistencePort.findChatroomListByUserId(user);

        for (UserChatroom userChatroom : userChatRooms) {
            userChatroomPersistencePort.deleteById(userChatroom.getId());
        }
    }

    @Override
    public UserChatroom findUserInChatRoom(User user, Chatroom chatroom) {
        return userChatroomPersistencePort.findByUserIdAndChatroomId(user, chatroom).orElseThrow();
    }

    @Override
    public List<UserChatroom> findChatRoomsUserParticipatesIn(User user) {
        return userChatroomPersistencePort.findChatroomListByUserId(user);
    }

    @Override
    public List<UserChatroom> findParticipantsInChatRoom(Chatroom chatroom) {
        return userChatroomPersistencePort.findUserListByChatRoomId(chatroom);
    }

    @Override
    public Boolean doesUserExistInChatRoom(User user, Chatroom chatroom) {
        return userChatroomPersistencePort.existsByUserIdAndChatRoomId(user, chatroom);
    }
}
