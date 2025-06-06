package org.opensource.book.api;

import dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Book", description = "Book API Document")
public class BookController implements BookApi {
    private final BookFacade bookFacade;

    @Override
    @GetMapping("/get-all-book")
    @Operation(summary = "베스트 50 API", description = "베스트 50 책 리스트를 불러옵니다.")
    public ApiResponse<List<BookResponseDto>> getAllBooks() {
        return ApiResponse.success(BookSuccessType.GET_ALL_BOOK, bookFacade.getAllBooks());
    }

    @Override
    @GetMapping("/get-book")
    @Operation(summary = "책 API", description = "책 한권의 정보를 불러옵니다.")
    public ApiResponse<BookResponseDto> getBookById(
            @RequestParam Long bookId) {
        return ApiResponse.success(BookSuccessType.GET_BOOK, bookFacade.getBook(bookId));
    }

    @Override
    @GetMapping("/get-book-by-title")
    @Operation(summary = "책 검색 API", description = "책 이름 검색으로 정보를 불러옵니다.")
    public ApiResponse<List<BookResponseDto>> getBookByTitle(
            @RequestParam String title) {
        return ApiResponse.success(BookSuccessType.GET_BOOK, bookFacade.getBookByTitle(title));
    }
}
