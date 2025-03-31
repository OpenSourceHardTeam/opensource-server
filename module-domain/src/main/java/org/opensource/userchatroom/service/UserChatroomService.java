package org.opensource.userchatroom.service;

import lombok.RequiredArgsConstructor;

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
                .findByUserIdAndChatroomId(command.user().getId(), command.chatroom().getId())
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
    public void deleteUserAtChatRoomByChatRoomId(Long userId, Long chatroomId) {
        UserChatroom userChatroom = userChatroomPersistencePort.findByUserIdAndChatroomId(userId, chatroomId).orElseThrow();
        userChatroomPersistencePort.deleteById(userChatroom.getId());
    }

    @Override
    public void deleteAllUsersWhenChatRoomDeleted(Long chatroomId) {
        List<UserChatroom> userChatRooms = userChatroomPersistencePort.findUserListByChatRoomId(chatroomId);

        for (UserChatroom userChatroom : userChatRooms) {
            userChatroomPersistencePort.deleteById(userChatroom.getId());
        }
    }

    @Override
    public void deleteAllChatroomsWhenUserDeleted(Long userId) {
        List<UserChatroom> userChatRooms = userChatroomPersistencePort.findChatroomListByUserId(userId);

        for (UserChatroom userChatroom : userChatRooms) {
            userChatroomPersistencePort.deleteById(userChatroom.getId());
        }
    }

    @Override
    public UserChatroom findUserInChatRoom(Long userId, Long chatRoomId) {
        return userChatroomPersistencePort.findByUserIdAndChatroomId(userId, chatRoomId).orElseThrow();
    }

    @Override
    public List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId) {
        return userChatroomPersistencePort.findChatroomListByUserId(userId);
    }

    @Override
    public List<UserChatroom> findParticipantsInChatRoom(Long chatRoomId) {
        return userChatroomPersistencePort.findUserListByChatRoomId(chatRoomId);
    }

    @Override
    public Boolean doesUserExistInChatRoom(Long userId, Long chatRoomId) {
        return userChatroomPersistencePort.existsByUserIdAndChatRoomId(userId, chatRoomId);
    }
}
