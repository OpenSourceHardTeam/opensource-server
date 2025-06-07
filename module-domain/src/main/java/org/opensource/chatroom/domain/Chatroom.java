package org.opensource.chatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

import java.time.LocalDateTime;

@Getter
@Builder
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

    public Chatroom updateTopic(String newTopic) {
        return Chatroom.builder()
                .id(this.id)
                .topic(newTopic)  // 새로운 토픽으로 업데이트
                .book(this.book)
                .build();
    }
}