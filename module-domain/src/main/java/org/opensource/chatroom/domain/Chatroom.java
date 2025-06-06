package org.opensource.chatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

import java.time.LocalDateTime;

@Getter
public class Chatroom {

    private String topic;
    private Book book;

    @Builder
    public Chatroom(
            String topic,
            Book book
    ) {
        this.topic = topic;
        this.book = book;
    }
}