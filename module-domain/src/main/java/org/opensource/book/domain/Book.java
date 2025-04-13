package org.opensource.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {
    private Long bookId;
    private Long bookRank;
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
        Long bookRank,
        String bookImageUrl,
        String bookTitle,
        String bookAuthor,
        String bookDescription,
        String publisherName,
        String publishDate,
        String publisherReview
    ) {
        this.bookId = bookId;
        this.bookRank = bookRank;
        this.bookImageUrl = bookImageUrl;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.publisherName = publisherName;
        this.publishDate = publishDate;
        this.publisherReview = publisherReview;
    }
}
