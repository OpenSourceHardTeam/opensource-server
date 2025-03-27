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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "external_room_id", nullable = false)
    private String externalRoomId;

    @Column(name = "topic", nullable = false)
    private String topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

//    private BookArqumentEntity bookArqument;

    @Column(name = "total_participants", nullable = false)
    private int totalParticipants;

    @Column private long messageSequence;

    @Column private int currentParticipants;

    @Column private int messageCount;

    @Column boolean isArchived = false;

    @Builder
    private ChatroomEntity(
            Long id,
            String externalRoomId,
            String topic,
            BookEntity book,
            int totalParticipants,
            long messageSequence,
            int currentParticipants,
            int messageCount,
            boolean isArchived) {
        this.id = id;
        this.externalRoomId = externalRoomId;
        this.topic = topic;
        this.book = book;
        this.totalParticipants = totalParticipants;
        this.messageSequence = messageSequence;
        this.currentParticipants = currentParticipants;
        this.messageCount = messageCount;
        this.isArchived = isArchived;
    }

    public static ChatroomEntity from(Chatroom chatroom) {
        return builder()
                .id(chatroom.getId())
                .externalRoomId(chatroom.getExternalRoomId())
                .topic(chatroom.getTopic())
                .book(BookEntity.from(chatroom.getBook()))
                .totalParticipants(chatroom.getTotalParticipants())
                .messageSequence(chatroom.getMessageSequence())
                .currentParticipants(chatroom.getCurrentParticipants())
                .messageCount(chatroom.getMessageCount())
                .isArchived(chatroom.isArchived())
                .build();
    }

    public Chatroom toModel() {
        return Chatroom.builder()
                .id(id)
                .externalRoomId(externalRoomId)
                .topic(topic)
                .book(this.book.toModel())
                .totalParticipants(totalParticipants)
                .messageSequence(messageSequence)
                .currentParticipants(currentParticipants)
                .messageCount(messageCount)
                .isArchived(isArchived)
                .build();
    }
}
