package com.fieldmanagement.fieldmanagement_be.user.usecase;

import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.UserDetail;
import com.fieldmanagement.fieldmanagement_be.user.exception.UserNotFoundException;
import com.fieldmanagement.fieldmanagement_be.common.port.FileService;
import com.fieldmanagement.fieldmanagement_be.common.util.SecurityUtils;
import com.fieldmanagement.fieldmanagement_be.user.adapter.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.UpdateProfileRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileUseCase {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    @Qualifier("image-local")
    private final FileService fileService;

    public UserResponse getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy email trong security"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateProfile(UpdateProfileRequest profileRequest) throws IOException {
        User user = userRepo.findById(profileRequest.getId())
                .orElseThrow(() ->
                        new UserNotFoundException("Không tìm thấy người dùng"));
        UserDetail userDetail = user.getUserDetail();

        if (profileRequest.getAvatar() != null && !profileRequest.getAvatar().isEmpty()) {
            String fileName = fileService.upload(profileRequest.getAvatar());
            fileService.delete(userDetail.getAvatar());
            userDetail.setAvatar(fileName);
        }

        userMapper.updateUser(userDetail, profileRequest);
        user.setUserDetail(userDetail);
        User saved = userRepo.save(user);
        return userMapper.toResponse(saved);
    }

    @Transactional
    public void deleteProfile() {
        String email = SecurityUtils.getUserEmailFromSecurity();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy email trong security"));
        userRepo.softDelete(user);
    }

    public UserResponse getOtherProfile(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm người dùng"));
        return userMapper.toResponse(user);
    }
}
