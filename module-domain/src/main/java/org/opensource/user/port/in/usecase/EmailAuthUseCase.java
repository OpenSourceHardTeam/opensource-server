package org.opensource.user.port.in.usecase;

public interface EmailAuthUseCase {
    void sendEmail(String email, long duration);

    void validateEmail(String email, String authCode);
}
