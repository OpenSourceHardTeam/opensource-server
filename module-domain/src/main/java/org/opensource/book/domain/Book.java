package org.opensource.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {
    
    private long id;

    @Builder
    public Book(long id) {
        this.id = id;
    }
}
