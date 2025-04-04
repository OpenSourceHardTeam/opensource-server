package org.opensource.user.port.out;

public interface PasswordEncoderPort {
    String encode(String password);

    Boolean matches(String rawPassword, String encodedPassword);
}
