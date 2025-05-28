package org.opensource.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.LoginType;
import org.opensource.user.domain.UserCredentials;

@Entity
@RequiredArgsConstructor
@Getter
public class UserCredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Builder
    private UserCredentialsEntity(
            Long id,
            UserEntity user,
            String password,
            LoginType loginType) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.loginType = loginType;
    }

    public static UserCredentialsEntity from(UserCredentials userCredentials) {
        return builder()
                .id(userCredentials.getId())
                .user(UserEntity.from(userCredentials.getUser()))
                .password(userCredentials.getPassword())
                .loginType(userCredentials.getLoginType())
                .build();
    }

    public UserCredentials toModel() {
        return UserCredentials.builder()
                .id(this.id)
                .user(this.user.toModel())
                .password(this.password)
                .loginType(this.loginType)
                .build();
    }
}
