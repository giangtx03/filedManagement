package com.fieldmanagement.fieldmanagement_be.user.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.user.adapter.db.JpaUserRepository;
import com.fieldmanagement.fieldmanagement_be.user.adapter.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class UserQueryPortImpl implements UserQueryPort {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toUser);
    }
}
