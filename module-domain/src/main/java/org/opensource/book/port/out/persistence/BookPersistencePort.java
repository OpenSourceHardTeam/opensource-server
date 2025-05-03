package org.opensource.book.port.out.persistence;

import org.opensource.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {
    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);
}
