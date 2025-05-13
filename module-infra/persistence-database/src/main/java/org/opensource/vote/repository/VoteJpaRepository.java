package org.opensource.vote.repository;

import org.opensource.book.entity.BookEntity;
import org.opensource.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteJpaRepository extends JpaRepository<VoteEntity, Long> {
    List<VoteEntity> findByBook_BookId(Long bookId);

    List<VoteEntity> findByBook_BookIdOrderByCreateAtDesc(Long bookId);

    List<VoteEntity> findByBook_BookIdOrderByVoteCountDesc(Long bookId);

    List<VoteEntity> findAllByOrderByCreateAtDesc();

    List<VoteEntity> findAllByOrderByVoteCountDesc();

    Long book(BookEntity book);
}
