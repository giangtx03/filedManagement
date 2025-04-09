package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.fieldmanagement.fieldmanagement_be.dao.repository.UserDetailRepo;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import com.fieldmanagement.fieldmanagement_be.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;
    private final UserMapper userMapper;

    @Override
    public UserResponse getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();

        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy email trong security")
                );
        return userMapper.toResponse(userModel.getUserDetail());
    }
}
