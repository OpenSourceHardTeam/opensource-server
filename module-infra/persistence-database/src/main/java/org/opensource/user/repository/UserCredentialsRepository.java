package org.opensource.user.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.UserCredentials;
import org.opensource.user.entity.UserCredentialsEntity;
import org.opensource.user.port.out.persistence.UserCredentialsPersistencePort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCredentialsRepository implements UserCredentialsPersistencePort {
    private final UserCredentialsJpaRepository userCredentialsJpaRepository;

    @Override
    public void save(UserCredentials userCredentials) {
        userCredentialsJpaRepository.save(UserCredentialsEntity.from(userCredentials));
    }
}
