package org.opensource.user.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.User;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.dto.response.SignUpResponseDto;
import org.opensource.user.mapper.UserMapper;
import org.opensource.user.port.in.command.UserCreateCommand;
import org.opensource.user.port.in.usecase.EmailAuthUseCase;
import org.opensource.user.port.in.usecase.UserUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacade {
    private final EmailAuthUseCase emailAuthUseCase;
    private final UserUseCase userUseCase;

    public void sendEmail(String email) {
        emailAuthUseCase.sendEmail(email, 5 * 60L);
    }

    public void validateEmail(String email, String authCode) {
        emailAuthUseCase.validateEmail(email, authCode);
    }

    public SignUpResponseDto signUp(String name, String email, String password) {
        User user = userUseCase.signUp(UserMapper.toUserCreateCommand(name, email, password));

        return SignUpResponseDto.of(user);
    }

    public void checkEmailExists(String email) {
        userUseCase.checkEmailExists(email);
    }

    public void checkNameExists(String name) {
        userUseCase.checkNameExists(name);
    }
}
