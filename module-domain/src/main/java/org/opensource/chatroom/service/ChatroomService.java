package org.opensource.chatroom.service;

import org.opensource.chatroom.port.in.usecase.ChatroomUpdateUsecase;
import org.opensource.chatroom.port.in.usecase.ChatroomUsecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatroomService implements ChatroomUsecase, ChatroomUpdateUsecase {

}
