package org.opensource.book.service;

import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.port.in.usecase.BookUsecase;
import org.opensource.book.port.out.persistence.BookPersistencePort;
import org.springframework.stereotype.Service;
import type.user.BookErrorType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements BookUsecase {
    private final BookPersistencePort bookPort;

    @Override
    public List<Book> findAllBooks() {
        try {
            return bookPort.getAllBooks();
        } catch (Exception e) {
            throw new BadRequestException(BookErrorType.GET_BOOK_INFO_ERROR);
        }
    }

    @Override
    public Book findBookById(Long id) {
        return bookPort.getBookById(id)
                .orElseThrow(() -> new BadRequestException(BookErrorType.GET_BOOK_INFO_ERROR));
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return bookPort.getBookByTitle(title);
    }
}
