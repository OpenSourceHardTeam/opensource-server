package org.opensource.user.repository;

import org.opensource.user.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsJpaRepository extends JpaRepository<UserCredentialsEntity, String> {
}
