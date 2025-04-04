package org.opensource.user.port.out.persistence;

import org.opensource.user.domain.UserCredentials;

import java.util.Optional;

public interface UserCredentialsPersistencePort {
    void save(UserCredentials userCredentials);

    Optional<UserCredentials> findByUserEmail(String email);
}
