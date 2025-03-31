package org.opensource.user.port.out.persistence;

import org.opensource.user.domain.User;

public interface UserPersistencePort {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    void save(User user);
}
