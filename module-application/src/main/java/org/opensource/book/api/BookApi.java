package org.opensource.book.api;

import dto.response.ApiResponse;
import org.opensource.book.dto.BookResponseDto;

import java.util.List;

public interface BookApi {
    ApiResponse<List<BookResponseDto>> getAllBooks();

    ApiResponse<BookResponseDto> getBookById(Long bookId);
}
