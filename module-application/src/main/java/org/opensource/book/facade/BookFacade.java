package org.opensource.book.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.book.domain.Book;
import org.opensource.book.dto.BookResponseDto;
import org.opensource.book.port.in.usecase.BookUsecase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookFacade {
    private final BookUsecase bookUsecase;

    public List<BookResponseDto> getAllBooks() {
        return bookUsecase.findAllBooks()
                .stream()
                .map(BookResponseDto::of)
                .toList();
    }

    public BookResponseDto getBook(Long id) {
        return BookResponseDto.of(bookUsecase.findBookById(id));
    }

    public List<BookResponseDto> getBookByTitle(String title) {
        return bookUsecase.findBookByTitle(title)
                .stream()
                .map(BookResponseDto::of)
                .toList();
    }
}
