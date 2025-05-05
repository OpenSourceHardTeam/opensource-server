package org.opensource.vote.repository;

import org.opensource.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteJpaRepository extends JpaRepository<VoteEntity, Long> {
    List<VoteEntity> findByBook_BookId(Long bookId);
}
