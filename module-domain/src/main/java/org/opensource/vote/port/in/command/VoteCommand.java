package org.opensource.vote.port.in.command;

public record VoteCommand(Long voteId, Long userId, Boolean answered) {
}
