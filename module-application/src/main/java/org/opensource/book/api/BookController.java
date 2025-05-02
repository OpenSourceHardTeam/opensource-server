package org.opensource.book.api;

import dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.opensource.book.dto.BookResponseDto;
import org.opensource.book.facade.BookFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import type.user.BookSuccessType;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController implements BookApi {
    private final BookFacade bookFacade;

    @Override
    @GetMapping("/get-all-book")
    public ApiResponse<List<BookResponseDto>> getAllBooks() {
        return ApiResponse.success(BookSuccessType.GET_ALL_BOOK, bookFacade.getAllBooks());
    }

    @Override
    @GetMapping("/get-book")
    public ApiResponse<BookResponseDto> getBookById(
            @RequestParam Long bookId) {
        return ApiResponse.success(BookSuccessType.GET_BOOK, bookFacade.getBook(bookId));
    }
}
