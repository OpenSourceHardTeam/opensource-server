package org.opensource.vote.port.out.persistence;

import org.opensource.vote.domain.Vote;
import org.opensource.vote.port.in.command.AddVoteCommand;

import java.util.List;
import java.util.Optional;

public interface VotePersistencePort {
    void vote(Vote vote);

    void addVote(Vote vote);

    List<Vote> findByBookIdOrderByCreateAt(Long bookId);

    List<Vote> findByBookIdOrderByVoteCount(Long bookId);

    Optional<Vote> findByVoteId(Long voteId);

    List<Vote> findAllByCreatedAt();

    List<Vote> findAllByVoteCount();
}
