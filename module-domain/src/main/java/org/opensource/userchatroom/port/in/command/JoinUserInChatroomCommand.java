package org.opensource.userchatroom.port.in.command;

public record JoinUserInChatroomCommand(Long userId, Long chatroomId) {
}
