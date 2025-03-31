package org.opensource.user.service;

import exception.BadRequestException;
import exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.LoginType;
import org.opensource.user.domain.User;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.port.in.command.UserCreateCommand;
import org.opensource.user.port.in.usecase.UserUseCase;
import org.opensource.user.port.out.PasswordEncoderPort;
import org.opensource.user.port.out.persistence.UserCredentialsPersistencePort;
import org.opensource.user.port.out.persistence.UserPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import type.user.UserErrorType;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final UserCredentialsPersistencePort userCredentialsPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    @Transactional
    public User signUp(UserCreateCommand userCreateCommand) {
        try {
            User user = User.builder()
                    .email(userCreateCommand.email())
                    .name(userCreateCommand.name())
                    .build();

            userPersistencePort.save(user);

            User savedUser = userPersistencePort.findByEmail(userCreateCommand.email());

            userCredentialsPersistencePort.save(UserCredentials.builder()
                    .user(savedUser)
                    .loginType(LoginType.NORMAL)
                    .password(passwordEncoderPort.encode(userCreateCommand.password()))
//                .refreshToken(null)
                    .build());

            return savedUser;
        } catch (InternalServerException e) {
            throw new InternalServerException(UserErrorType.SIGN_UP_ERROR);
        }

    }

    @Override
    @Transactional
    public void checkEmailExists(String email) {
        if (userPersistencePort.existsByEmail(email)) {
            throw new BadRequestException(UserErrorType.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void checkNameExists(String name) {
        if (userPersistencePort.existsByName(name)) {
            throw new BadRequestException(UserErrorType.NAME_ALREADY_EXISTS);
        }
    }
}
