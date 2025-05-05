package org.opensource.vote.port.in.command;

public record AddVoteCommand(Long bookId, String title, String content) {
}
