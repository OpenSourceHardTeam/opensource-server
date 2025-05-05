package org.opensource.vote.repository;

import org.opensource.vote.entity.VoteEntity;
import org.opensource.vote.entity.VoteUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteUserJpaRepository extends JpaRepository<VoteUserEntity, Long> {
    Optional<VoteUserEntity> findByUserIdAndVote_VoteId(Long userId, Long voteId);

    Boolean existsByUserIdAndVote_VoteId(Long userId, Long voteId);
}
