package com.fieldmanagement.fieldmanagement_be.user.adapter.db.impl;

import com.fieldmanagement.fieldmanagement_be.user.adapter.db.JpaUserRepository;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserDetailEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.db.entity.UserEntity;
import com.fieldmanagement.fieldmanagement_be.user.adapter.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Primary
@Repository("jpa")
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        UserDetailEntity detailEntity = userMapper.toUserDetailEntity(user.getUserDetail());

        userEntity.setUserDetail(detailEntity);
        detailEntity.setUser(userEntity);
        UserEntity saved = jpaUserRepository.save(userEntity);
        return userMapper.toUser(saved);
    }

    @Override
    public Optional<User> findById(String id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toUser);
    }


    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
