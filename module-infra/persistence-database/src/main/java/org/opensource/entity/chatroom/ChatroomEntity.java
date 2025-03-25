package org.opensource.entity.chatroom;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.chatroom.domain.Chatroom;
import org.opensource.chatroom.domain.ChatroomType;
import org.opensource.entity.base.BaseTimeEntity;
import org.opensource.entity.book.BookEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private long id;

    @Column(name = "external_room_id", nullable = false)
    private String externalRoomId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChatroomType type;

    @Column(name = "topic", length = 100)
    private String topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Column(name = "total_participants", nullable = false)
    private int totalParticipants;

    @Column private String password;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column private long messageSequence;

    @Column private int currentParticipants;

    @Column private int messageCount;

    @Column boolean isArchived = false;

    @Builder
    public ChatroomEntity(
            Long id,
            String externalRoomId,
            String name,
            ChatroomType type,
            String topic,
            BookEntity book,
            int totalParticipants,
            String password,
            LocalDateTime expiryDate,
            long messageSequence,
            int currentParticipants,
            int messageCount,
            boolean isArchived) {
        this.id = id;
        this.externalRoomId = externalRoomId;
        this.name = name;
        this.type = type;
        this.topic = topic;
        this.book = book;
        this.totalParticipants = totalParticipants;
        this.password = password;
        this.expiryDate = expiryDate;
        this.messageSequence = messageSequence;
        this.currentParticipants = currentParticipants;
        this.messageCount = messageCount;
        this.isArchived = isArchived;
    }

    // Chatroom domain to ChatroomEntity
    public static ChatroomEntity from(Chatroom chatroom) {
        return builder()
                .id(chatroom.getId())
                .externalRoomId(chatroom.getExternalRoomId())
                .name(chatroom.getName())
                .type(chatroom.getType())
                .topic(chatroom.getTopic())
                .book(BookEntity.from(chatroom.getBook()))
                .totalParticipants(chatroom.getTotalParticipants())
                .password(chatroom.getPassword())
                .expiryDate(chatroom.getExpiryDate())
                .messageSequence(chatroom.getMessageSequence())
                .currentParticipants(chatroom.getCurrentParticipants())
                .messageCount(chatroom.getMessageCount())
                .isArchived(chatroom.isArchived())
                .build();
    }

    // ChatroomEntity to Chatroom domain
    public Chatroom toModel() {
        return Chatroom.builder()
                .id(id)
                .externalRoomId(externalRoomId)
                .name(name)
                .type(type)
                .topic(topic)
                .book(this.book.toModel())
                .totalParticipants(totalParticipants)
                .password(password)
                .expiryDate(expiryDate)
                .messageSequence(messageSequence)
                .currentParticipants(currentParticipants)
                .messageCount(messageCount)
                .isArchived(isArchived)
                .build();
    }
}
