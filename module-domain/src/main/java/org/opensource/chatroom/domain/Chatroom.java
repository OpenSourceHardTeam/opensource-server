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
    private Integer totalParticipants;
    private Long messageSequence;
    private Integer currentParticipants;
    private Integer messageCount;
    private Boolean isArchived;

    @Builder
    public Chatroom(
            Long id,
            String externalRoomId,
            String topic,
            Book book,
            Integer totalParticipants,
            Long messageSequence,
            Integer currentParticipants,
            Integer messageCount,
            Boolean isArchived
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