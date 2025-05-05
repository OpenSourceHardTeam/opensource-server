package org.opensource.vote.port.in.usecase;

import org.opensource.vote.port.in.command.VoteCommand;

public interface VoteUserUsecase {
    void voteUser(VoteCommand voteCommand);

    Boolean userAnswered(Long userId, Long voteId);
}
