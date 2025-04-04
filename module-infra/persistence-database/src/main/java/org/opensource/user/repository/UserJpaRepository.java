package org.opensource.user.repository;

import org.opensource.user.domain.User;
import org.opensource.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    Boolean existsByName(String name);

    Optional<User> findByEmail(String email);
}
