package org.opensource.chatroom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.basetime.BaseTimeEntity;
import org.opensource.book.entity.BookEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Builder
    private ChatroomEntity(
            Long id,
            String topic,
            BookEntity book) {
        this.topic = topic;
        this.book = book;
    }

    public static ChatroomEntity from(Chatroom chatroom) {
        return builder()
                .id(chatroom.getId())
                .topic(chatroom.getTopic())
                .book(BookEntity.from(chatroom.getBook()))
                .build();
    }

    public Chatroom toModel() {
        return Chatroom.builder()
                .id(this.id)
                .topic(topic)
                .book(this.book.toModel())
                .build();
    }
}
