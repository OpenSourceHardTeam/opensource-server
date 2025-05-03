package org.opensource.book.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.entity.BookEntity;
import org.opensource.book.port.out.persistence.BookPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository implements BookPort {
    private final BookJpaRepository repository;

    @Override
    public List<Book> getAllBooks() {
        return repository.findAllByOrderByBookRankAsc()
                .stream()
                .map(BookEntity::toModel)
                .toList();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return repository.findById(id).map(BookEntity::toModel);
    }
}
