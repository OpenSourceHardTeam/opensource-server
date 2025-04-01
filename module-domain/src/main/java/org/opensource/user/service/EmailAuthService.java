package org.opensource.user.service;


import exception.BadRequestException;
import exception.InternalServerException;
import exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.opensource.user.port.in.command.EmailAuthCommand;
import org.opensource.user.port.in.usecase.EmailAuthUseCase;
import org.opensource.user.port.out.EmailSenderPort;
import org.opensource.user.port.out.persistence.EmailAuthPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import type.user.UserErrorType;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthService implements EmailAuthUseCase {
    private final EmailAuthPersistencePort emailAuthPersistencePort;
    private final EmailSenderPort emailSenderPort;

    @Override
    public void sendEmail(String email, long duration) {
        if (emailAuthPersistencePort.existData(email)) {
            emailAuthPersistencePort.deleteData(email);
        }

        try {
            String authCode = createAuthCode();
            EmailAuthCommand command = EmailAuthCommand.builder()
                    .email(email)
                    .authCode(authCode)
                    .duration(duration)
                    .build();
            emailAuthPersistencePort.setData(command);
            emailSenderPort.sendEmail(command.email(), authCode);
        } catch (Exception e) {
            throw new InternalServerException(UserErrorType.SEND_EMAIL_ERROR);
        }
    }

    @Override
    public void validateEmail(String email, String authCode) {
        String storeCode = emailAuthPersistencePort.getData(email);
        if (storeCode == null) {
            throw new BadRequestException(UserErrorType.EMAIL_AUTH_TIMEOUT_ERROR);
        }

        if (!storeCode.equals(authCode)) {
            throw new UnauthorizedException(UserErrorType.CHECK_EMAIL_VALIDATION_ERROR);
        }
    }

    private String createAuthCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
