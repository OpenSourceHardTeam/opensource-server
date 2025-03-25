package org.opensource.chatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

import java.time.LocalDateTime;

@Getter
public class Chatroom {

    private long id;
    private String externalRoomId;
    private String name;
    private ChatroomType type;
    private String topic;
    private Book book;
    private int totalParticipants;
    private String password;
    private LocalDateTime expiryDate;
    private long messageSequence;
    private int currentParticipants;
    private int messageCount;
    private boolean isArchived;

    @Builder
    public Chatroom(
            long id,
            String externalRoomId,
            String name,
            ChatroomType type,
            String topic,
            Book book,
            int totalParticipants,
            String password,
            LocalDateTime expiryDate,
            long messageSequence,
            int currentParticipants,
            int messageCount,
            boolean isArchived
    ) {
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
}
