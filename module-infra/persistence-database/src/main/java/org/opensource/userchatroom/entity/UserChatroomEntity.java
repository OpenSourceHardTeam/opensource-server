package org.opensource.userchatroom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.user.entity.UserEntity;
import org.opensource.userchatroom.domain.UserChatroom;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChatroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatroomEntity chatroom;

    private Boolean isOnline;

    @Builder
    private UserChatroomEntity(UserEntity user, ChatroomEntity chatroom, Boolean isOnline) {
        this.user = user;
        this.chatroom = chatroom;
        this.isOnline = isOnline;
    }

    public static UserChatroomEntity from(UserChatroom userChatroom) {
        return builder().
                user(UserEntity.from(userChatroom.getUser())).
                chatroom(ChatroomEntity.from(userChatroom.getChatroom())).
                isOnline(userChatroom.getIsOnline()).
                build();
    }

    public UserChatroom toModel() {
        return UserChatroom.builder()
                .user(user.toModel())
                .chatroom(chatroom.toModel())
                .isOnline(isOnline)
                .build();
    }
}
