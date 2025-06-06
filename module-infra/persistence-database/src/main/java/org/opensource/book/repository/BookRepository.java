package org.opensource.book.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.entity.BookEntity;
import org.opensource.book.port.out.persistence.BookPersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository implements BookPersistencePort {
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

    @Override
    public List<Book> getBookByTitle(String title) {
        return repository.searchBookEntityByBookTitle(title)
                .stream()
                .map(BookEntity::toModel)
                .toList();
    }
}
