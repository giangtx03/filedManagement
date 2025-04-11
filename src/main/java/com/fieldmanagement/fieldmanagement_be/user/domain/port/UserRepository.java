package com.fieldmanagement.fieldmanagement_be.user.domain.port;

import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
