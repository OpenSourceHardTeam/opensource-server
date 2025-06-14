package org.opensource.vote.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.vote.domain.Vote;
import org.opensource.vote.entity.VoteEntity;
import org.opensource.vote.port.out.persistence.VotePersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteRepository implements VotePersistencePort {
    private final VoteJpaRepository voteJpaRepository;

    @Override
    public void vote(Vote vote) {
        voteJpaRepository.save(VoteEntity.from(vote));
    }

    @Override
    public void addVote(Vote vote) {
        voteJpaRepository.save(VoteEntity.from(vote));
    }

    @Override
    public List<Vote> findByBookIdOrderByCreateAt(Long bookId) {
        return voteJpaRepository.findByBook_BookIdOrderByCreateAtDesc(bookId)
                .stream()
                .map(VoteEntity::toModel)
                .toList();
    }

    @Override
    public List<Vote> findByBookIdOrderByVoteCount(Long bookId) {
        return voteJpaRepository.findByBook_BookIdOrderByVoteCountDesc(bookId)
                .stream()
                .map(VoteEntity::toModel)
                .toList();
    }

    @Override
    public Optional<Vote> findByVoteId(Long voteId) {
        return voteJpaRepository.findById(voteId).map(VoteEntity::toModel);
    }

    @Override
    public List<Vote> findAllByCreatedAt() {
        return voteJpaRepository.findAllByOrderByCreateAtDesc()
                .stream()
                .map(VoteEntity::toModel)
                .toList();
    }

    @Override
    public List<Vote> findAllByVoteCount() {
        return voteJpaRepository.findAllByOrderByVoteCountDesc()
                .stream()
                .map(VoteEntity::toModel)
                .toList();
    }

    @Override
    public void deleteByVoteId(Long voteId) {
        voteJpaRepository.deleteById(voteId);
    }
}
