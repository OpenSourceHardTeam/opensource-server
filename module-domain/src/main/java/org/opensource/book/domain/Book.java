package org.opensource.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {

    private Long id;

    @Builder
    public Book(Long id) {
        this.id = id;
    }
}
