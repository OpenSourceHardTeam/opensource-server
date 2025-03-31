package org.opensource.userchatroom.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.userchatroom.port.in.usecase.UserChatroomQueryUsecase;
import org.opensource.userchatroom.port.in.usecase.UserChatroomUsecase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatroomFacade {

    private final UserChatroomQueryUsecase userChatroomQueryUsecase;
    private final UserChatroomUsecase userChatroomUsecase;


}
