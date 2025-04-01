package org.opensource.chatroom.port.in.command;

public record CreateChatroomCommand(String topic, Long bookId, Long bookArgumentId) {
}
