package org.opensource.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opensource.book.domain.Book;

@Getter
@AllArgsConstructor
public class BookResponseDto {
    private Long bookId;
    private Long bookRank;
    private String bookImageUrl;
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    private String publisherName;
    private String publishDate;
    private String publisherReview;

    public static BookResponseDto of(Book book) {
        return new BookResponseDto(
                book.getBookId(),
                book.getBookRank(),
                book.getBookImageUrl(),
                book.getBookTitle(),
                book.getBookAuthor(),
                book.getBookDescription(),
                book.getPublisherName(),
                book.getPublishDate(),
                book.getPublisherReview());
    }
}
