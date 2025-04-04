package org.opensource.user.service;

import exception.BadRequestException;
import exception.InternalServerException;
import exception.NotFoundException;
import exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.LoginType;
import org.opensource.user.domain.User;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.port.in.command.UserCreateCommand;
import org.opensource.user.port.in.command.UserSignInCommand;
import org.opensource.user.port.in.usecase.UserUseCase;
import org.opensource.user.port.out.PasswordEncoderPort;
import org.opensource.user.port.out.SecurityPort;
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
    private final SecurityPort securityPort;

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
    public String signIn(UserSignInCommand userSignInCommand) {
        UserCredentials userCredentials = userCredentialsPersistencePort.findByUserEmail(userSignInCommand.email())
                .orElseThrow(() -> new NotFoundException(UserErrorType.EMAIL_NOT_EXIST));

        if (!passwordEncoderPort.matches(userSignInCommand.password(), userCredentials.getPassword())) {
            throw new UnauthorizedException(UserErrorType.USER_PASSWORD_NOT_MATCH);
        }

        return securityPort.createToken(userCredentials);
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

    @Override
    public User findUserByEmail(String email) {
        return userPersistencePort.findByEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        User user = userPersistencePort.findById(id)
                .orElseThrow(() -> new BadRequestException(UserErrorType.USER_NOT_EXIST));
        return user;
    }
}
