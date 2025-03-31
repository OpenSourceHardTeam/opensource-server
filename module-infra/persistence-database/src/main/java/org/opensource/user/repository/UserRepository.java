package org.opensource.user.repository;

import lombok.RequiredArgsConstructor;
import org.opensource.user.domain.User;
import org.opensource.user.entity.UserEntity;
import org.opensource.user.port.out.persistence.UserPersistencePort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersistencePort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByName(String name) {
        return userJpaRepository.existsByName(name);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(UserEntity.from(user));
    }
}
