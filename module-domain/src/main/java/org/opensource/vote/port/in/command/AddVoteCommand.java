package org.opensource.vote.port.in.command;

public record AddVoteCommand(Long bookId, Long userId, String title, String content) {
}
