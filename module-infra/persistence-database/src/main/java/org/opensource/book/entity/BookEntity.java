package org.opensource.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.opensource.book.domain.Book;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    // 책 중복 확인용
    @Column(name = "book_cmdt_code", unique = true, nullable = false)
    private Long bookCmdtCode;

    @Column(name = "book_rank")
    private Long bookRank;

    @Column(name = "book_image_url")
    private String bookImageUrl;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_description", columnDefinition = "TEXT")
    private String bookDescription;

    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "publish_date")
    private String publishDate;

    @Column(name = "publisher_review", columnDefinition = "TEXT")
    private String publisherReview;

    @Builder
    public BookEntity(
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


    public static BookEntity from(Book book) {
        return builder()
                .bookId(book.getBookId())
                .bookRank(book.getBookRank())
                .bookImageUrl(book.getBookImageUrl())
                .bookTitle(book.getBookTitle())
                .bookAuthor(book.getBookAuthor())
                .bookDescription(book.getBookDescription())
                .publisherName(book.getPublisherName())
                .publishDate(book.getPublishDate())
                .publisherReview(book.getPublisherReview())
                .build();
    }

    public Book toModel() {
        return Book.builder()
                .bookId(bookId)
                .bookRank(bookRank)
                .bookImageUrl(bookImageUrl)
                .bookTitle(bookTitle)
                .bookAuthor(bookAuthor)
                .bookDescription(bookDescription)
                .publisherName(publisherName)
                .publishDate(publishDate)
                .publisherReview(publisherReview)
                .build();
    }
}
