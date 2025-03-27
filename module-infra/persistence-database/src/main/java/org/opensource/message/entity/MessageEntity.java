package org.opensource.message.entity;

import jakarta.persistence.*;
import lombok.*;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.message.domain.Message;
import org.opensource.user.entity.UserEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatroomEntity chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String content;

    private String externalMessageId;

    private Long serverNodeId;

    @Builder
    private MessageEntity(
            Long id,
            ChatroomEntity chatroom,
            UserEntity user,
            String content,
            String externalMessageId,
            Long serverNodeId) {
        this.id = id;
        this.chatroom = chatroom;
        this.user = user;
        this.content = content;
        this.externalMessageId = externalMessageId;
        this.serverNodeId = serverNodeId;
    }

    public static MessageEntity from(Message message) {
        return builder()
                .id(message.getId())
                .chatroom(ChatroomEntity.from(message.getChatroom()))
                .user(UserEntity.from(message.getUser()))
                .content(message.getContent())
                .externalMessageId(message.getExternalMessageId())
                .serverNodeId(message.getServerNodeId())
                .build();
    }

    public Message toModel() {
        return Message.builder()
                .id(id)
                .chatroom(chatroom.toModel())
                .user(this.user.toModel())
                .content(content)
                .externalMessageId(externalMessageId)
                .serverNodeId(serverNodeId)
                .build();
    }
}
