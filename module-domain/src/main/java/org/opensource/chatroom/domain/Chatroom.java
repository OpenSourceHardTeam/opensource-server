package org.opensource.chatroom.domain;

import lombok.Builder;
import lombok.Getter;
import org.opensource.book.domain.Book;

import java.time.LocalDateTime;

@Getter
public class Chatroom {

    private Long id;
    private String externalRoomId;
    private String topic;
    private Book book;
//    private BookArgument bookArgument;
    private int totalParticipants;
    private long messageSequence;
    private int currentParticipants;
    private int messageCount;
    private boolean isArchived;

    @Builder
    public Chatroom(
            Long id,
            String externalRoomId,
            String topic,
            Book book,
            int totalParticipants,
            long messageSequence,
            int currentParticipants,
            int messageCount,
            boolean isArchived
    ) {
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
}
