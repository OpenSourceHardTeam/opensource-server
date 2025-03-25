package org.opensource.chatroom.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Chatroom {
    private long id;
    private String externalRoomId;
    private String name;
    private ChatroomType type;
    private String topic;
    private Long bookId;
    private int maxParticipants;
    private String password;
    private LocalDateTime expiryDate;
    private long messageSequence;
    private int currentParticipants;
    private int messageCount;
    private boolean isArchived;
}
