package org.opensource.userchatroom.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.chatroom.facade.ChatroomFacade;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.domain.UserChatroom;
import org.opensource.userchatroom.port.in.usecase.UserChatroomQueryUsecase;
import org.opensource.userchatroom.port.in.usecase.UserChatroomUsecase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatroomFacade {

    private final UserChatroomQueryUsecase userChatroomQueryUsecase;
    private final UserChatroomUsecase userChatroomUsecase;
    private final ChatroomFacade chatroomFacade;
//    private final UserFacade userFacade;

//    public UserChatroom findUserInChatroom(Long userId, Long chatroomId) {
//        User user =
//    }

}
