package org.opensource.user.port.out;

import org.opensource.user.domain.UserCredentials;

public interface SecurityPort {
    String createToken(UserCredentials userCredentials);

    Boolean verifyToken(String token);

    String getJwtContents(String token);
}
