package org.opensource.user.facade;

import lombok.RequiredArgsConstructor;
import org.opensource.user.port.in.usecase.EmailAuthUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacade {
    private final EmailAuthUseCase emailAuthUseCase;

    public void sendEmail(String email) {
        emailAuthUseCase.sendEmail(email, 5 * 60L);
    }

    public void validateEmail(String email, String authCode) {
        emailAuthUseCase.validateEmail(email, authCode);
    }
}
