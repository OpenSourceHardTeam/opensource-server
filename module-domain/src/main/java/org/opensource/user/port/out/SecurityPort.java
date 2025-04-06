package org.opensource.user.port.out;

import org.opensource.user.domain.UserCredentials;

public interface SecurityPort {
    String createToken(UserCredentials userCredentials);

    void verifyToken(String token);

    Long getUserIdFromToken(String token);
}
