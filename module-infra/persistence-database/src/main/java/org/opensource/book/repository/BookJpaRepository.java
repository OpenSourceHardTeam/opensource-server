package org.opensource.book.repository;

import org.opensource.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByOrderByBookRankAsc();

    @Query("SELECT b FROM BookEntity b WHERE REPLACE(LOWER(b.bookTitle), ' ', '') LIKE LOWER(CONCAT('%', REPLACE(:keyword, ' ', ''), '%'))")
    List<BookEntity> searchBookEntityByBookTitle(@Param("keyword") String keyword);
}
