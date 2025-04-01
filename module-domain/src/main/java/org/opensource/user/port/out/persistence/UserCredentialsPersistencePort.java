package org.opensource.user.port.out.persistence;

import org.opensource.user.domain.UserCredentials;

public interface UserCredentialsPersistencePort {
    void save(UserCredentials userCredentials);
}
