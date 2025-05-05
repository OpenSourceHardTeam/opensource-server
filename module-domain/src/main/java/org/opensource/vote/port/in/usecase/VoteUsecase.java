package org.opensource.vote.port.in.usecase;

import org.opensource.vote.domain.Vote;
import org.opensource.vote.port.in.command.AddVoteCommand;
import org.opensource.vote.port.in.command.VoteCommand;

import java.util.List;

public interface VoteUsecase {
    void vote(VoteCommand command);

    void addVote(AddVoteCommand command);

    List<Vote> findAllVoteByBookId(Long bookId);
}
