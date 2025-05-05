package org.opensource.vote.mapper;

import org.opensource.vote.domain.Vote;
import org.opensource.vote.port.in.command.AddVoteCommand;
import org.opensource.vote.port.in.command.VoteCommand;

public class VoteMapper {
    public static VoteCommand toVoteCommand(
            Long voteId, Long userId, Boolean answered
    ) {
        return new VoteCommand(voteId, userId, answered);
    }

    public static AddVoteCommand toAddVoteCommand(
            Long bookId, String title, String content
    ) {
        return new AddVoteCommand(bookId, title, content);
    }
}
