package org.opensource.vote.port.out.persistence;

import org.opensource.vote.domain.VoteUser;

import java.util.Optional;

public interface VoteUserPersistencePort {
    void addVoteUser(VoteUser voteUser);

    void changeAnswer(VoteUser voteUser);

    Optional<VoteUser> findUserVotedByUserIdAndVoteId(Long userId, Long voteId);

    Boolean isUserVotedByUserIdAndVoteId(Long userId, Long voteId);

    void deleteUserVotedByVoteId(Long voteId);
}
