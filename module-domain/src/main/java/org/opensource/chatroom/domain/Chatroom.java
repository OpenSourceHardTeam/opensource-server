package org.opensource.chatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

import java.time.LocalDateTime;

@Getter
public class Chatroom {

    private Long id;
    private String topic;
    private Book book;

    @Builder
    public Chatroom(
            Long id,
            String topic,
            Book book
    ) {
        this.id = id;
        this.topic = topic;
        this.book = book;
    }
}