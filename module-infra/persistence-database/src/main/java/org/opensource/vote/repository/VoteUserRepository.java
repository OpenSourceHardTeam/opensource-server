package org.opensource.vote.repository;

import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.opensource.vote.domain.VoteUser;
import org.opensource.vote.entity.VoteUserEntity;
import org.opensource.vote.port.out.persistence.VoteUserPersistencePort;
import org.springframework.stereotype.Repository;
import type.user.VoteErrorType;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteUserRepository implements VoteUserPersistencePort {
    private final VoteUserJpaRepository voteUserJpaRepository;

    @Override
    public void addVoteUser(VoteUser voteUser) {
        voteUserJpaRepository.save(VoteUserEntity.from(voteUser));
    }

    @Override
    public void changeAnswer(VoteUser voteUser) {
        voteUserJpaRepository.save(VoteUserEntity.from(voteUser));
    }

    @Override
    public Optional<VoteUser> findUserVotedByUserIdAndVoteId(Long userId, Long voteId) {
        return voteUserJpaRepository.findByUserIdAndVote_VoteId(userId, voteId)
                .map(VoteUserEntity::toModel);
    }

    @Override
    public Boolean isUserVotedByUserIdAndVoteId(Long userId, Long voteId) {
        return voteUserJpaRepository.existsByUserIdAndVote_VoteId(userId, voteId);
    }
}
