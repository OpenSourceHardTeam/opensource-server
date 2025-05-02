package org.opensource.book.port.out;

import org.opensource.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookPort {
    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);
}
