package com.fieldmanagement.fieldmanagement_be.user.adapter.db;

import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
