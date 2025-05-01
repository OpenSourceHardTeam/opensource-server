package org.opensource.book.repository;

import org.opensource.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByOrderByBookRankAsc();
}
