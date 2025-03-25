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
    private long id;

    @Builder
    public BookEntity(Long id) {
        this.id = id;
    }


    public static BookEntity from(Book book) {
        return builder()
                .id(book.getId())
                .build();
    }

    public Book toModel() {
        return Book.builder()
                .id(id)
                .build();
    }
}
