package org.opensource.user.port.out;

public interface EmailSenderPort {
    void sendEmail(String email, String authCode);
}
