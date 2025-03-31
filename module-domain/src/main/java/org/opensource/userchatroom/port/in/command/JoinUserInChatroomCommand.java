package org.opensource.userchatroom.port.in.command;

import org.opensource.chatroom.domain.Chatroom;
import org.opensource.user.domain.User;

public record JoinUserInChatroomCommand(
        User user, Chatroom chatroom, Boolean isOnline) {
}
