package org.opensource.book.port.in.usecase;

import org.opensource.book.domain.Book;

import java.util.List;

public interface BookUsecase {
    List<Book> findAllBooks();

    Book findBookById(Long id);

    List<Book> findBookByTitle(String title);
}
