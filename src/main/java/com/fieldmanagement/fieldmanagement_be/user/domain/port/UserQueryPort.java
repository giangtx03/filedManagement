package com.fieldmanagement.fieldmanagement_be.user.domain.port;

import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;

import java.util.Optional;

public interface UserQueryPort {
    Optional<User> findByEmail(String email);
}
