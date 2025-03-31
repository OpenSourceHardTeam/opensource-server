package org.opensource.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.user.domain.User;
import org.opensource.userchatroom.entity.UserChatroomEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder
    private UserEntity(String userName) {
        this.name = userName;
    }

    public static UserEntity from(User user) {
        return builder()
                .userName(user.getName())
                .build();
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
