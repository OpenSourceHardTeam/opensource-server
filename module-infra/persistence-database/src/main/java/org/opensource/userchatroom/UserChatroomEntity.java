package org.opensource.userchatroom;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.chatroom.entity.ChatroomEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

}
