package org.opensource.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {
    private Long bookId;
    private String bookImageUrl;
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    private String publisherName;
    private String publishDate;
    private String publisherReview;

    @Builder
    public Book(
        Long bookId,
        String bookImageUrl,
        String bookTitle,
        String bookAuthor,
        String bookDescription,
        String publisherName,
        String publishDate,
        String publisherReview
    ) {
        this.bookId = bookId;
        this.bookImageUrl = bookImageUrl;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.publisherName = publisherName;
        this.publishDate = publishDate;
        this.publisherReview = publisherReview;
    }
}
