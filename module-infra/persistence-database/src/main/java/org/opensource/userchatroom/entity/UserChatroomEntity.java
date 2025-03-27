package org.opensource.userchatroom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.chatroom.entity.ChatroomEntity;
import org.opensource.userchatroom.domain.UserChatroom;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChatroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chatroom_id")
    private Integer id;

//    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatroomEntity chatroom;

    private Boolean isOnline;

    @Builder
    private UserChatroomEntity(ChatroomEntity chatroom, Boolean isOnline) {
        this.chatroom = chatroom;
        this.isOnline = isOnline;
    }

    public static UserChatroomEntity create(UserChatroom userChatroom) {
        return builder().
                chatroom(ChatroomEntity.from(userChatroom.getChatroom())).
                isOnline(userChatroom.getIsOnline()).
                build();
    }

    public UserChatroom toModel() {
        return UserChatroom.builder()
                .chatroom(chatroom.toModel())
                .isOnline(isOnline)
                .build();
    }
}
