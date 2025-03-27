package org.opensource.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.opensource.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Builder
    public UserEntity(String userName) {
        this.userName = userName;
    }

    public static UserEntity from(User user) {
        return builder()
                .userName(user.getUserName())
                .build();
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .userName(this.userName)
                .build();
    }
}
