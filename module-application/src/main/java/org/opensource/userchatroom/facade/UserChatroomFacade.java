package org.opensource.userchatroom.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.facade.ChatroomFacade;
import org.opensource.user.domain.User;
import org.opensource.user.facade.UserFacade;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.usecase.UserChatroomQueryUsecase;
import org.opensource.userchatroom.port.in.usecase.UserChatroomUsecase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatroomFacade {

    private final UserChatroomUsecase userChatroomUsecase;
    private final UserChatroomQueryUsecase userChatroomQueryUsecase;
    private final ChatroomFacade chatroomFacade;
    private final UserFacade userFacade;

    @Transactional
    public Long joinUserInChatRoom(Long userId, Long chatroomId, Boolean isOnline) {
        User user = userFacade.findUser(userId);
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        return userChatroomUsecase.joinUserInChatroom(user, chatroom, isOnline);
    }

    @Transactional
    public void deleteUserAtChatRoomByChatRoomId(Long userId, Long chatroomId) {
        User user = userFacade.findUser(userId);
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        userChatroomUsecase.deleteUserAtChatRoomByChatRoomId(user, chatroom);
    }

    @Transactional
    public void deleteAllUsersWhenChatRoomDeleted(Long chatroomId) {
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        userChatroomUsecase.deleteAllUsersWhenChatRoomDeleted(chatroom);
    }

    @Transactional
    public void deleteAllChatRoomsWhenUserDeleted(Long userId) {
        User user = userFacade.findUser(userId);
        userChatroomUsecase.deleteAllChatRoomsWhenUserDeleted(user);
    }

    @Transactional
    public Boolean doesUserExistInChatRoom(Long userId, Long chatroomId) {
        User user = userFacade.findUser(userId);
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        return userChatroomUsecase.doesUserExistInChatRoom(user, chatroom);
    }

    @Transactional(readOnly = true)
    public UserChatroom findUserInChatRoom(Long userId, Long chatroomId) {
        User user = userFacade.findUser(userId);
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        return userChatroomQueryUsecase.findUserInChatRoom(user, chatroom);
    }

    @Transactional(readOnly = true)
    public List<UserChatroom> findChatRoomsUserParticipatesIn(Long userId) {
        User user = userFacade.findUser(userId);
        return userChatroomQueryUsecase.findChatRoomsUserParticipatesIn(user);
    }

    @Transactional(readOnly = true)
    public List<UserChatroom> findParticipantsInChatRoom(Long chatroomId) {
        Chatroom chatroom = chatroomFacade.findChatroomById(chatroomId);
        return userChatroomQueryUsecase.findParticipantsInChatRoom(chatroom);
    }
}
