package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserDetailRepo;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.model.request.user.UpdateProfileRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.FileService;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import com.fieldmanagement.fieldmanagement_be.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Override
    public UserResponse getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();

        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy email trong security")
                );
        return userMapper.toResponse(userModel.getUserDetail());
    }

    @Override
    public UserResponse updateProfile(UpdateProfileRequest profileRequest) throws IOException {
        UserModel userModel = userRepo.findById(profileRequest.getId())
                .orElseThrow(() ->
                        new UserNotFoundException("Không tìm thấy người dùng"));
        UserDetailModel userDetailModel = userModel.getUserDetail();

        if (profileRequest.getAvatar() != null && !profileRequest.getAvatar().isEmpty()) {
            String fileName = fileService.upload(profileRequest.getAvatar());
            fileService.delete(userDetailModel.getAvatar());
            userDetailModel.setAvatar(fileName);
        }

        userMapper.updateUser(userDetailModel, profileRequest);
        UserDetailModel saveUser = userDetailRepo.save(userDetailModel);
        return userMapper.toResponse(saveUser);
    }
}
