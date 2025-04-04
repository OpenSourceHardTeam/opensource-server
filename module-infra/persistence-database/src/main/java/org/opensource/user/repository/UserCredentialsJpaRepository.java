package org.opensource.user.repository;

import org.opensource.user.domain.UserCredentials;
import org.opensource.user.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCredentialsJpaRepository extends JpaRepository<UserCredentialsEntity, String> {
    Optional<UserCredentialsEntity> findByUser_Email(String userEmail);
}
