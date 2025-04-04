package org.opensource.user.port.out.persistence;

import org.opensource.user.domain.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    void save(User user);

    Optional<User> findById(Long id);
}
