package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.fieldmanagement.commom.exception.EmailExistsException;
import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.commom.model.constant.EmailConstant;
import com.fieldmanagement.commom.model.enums.KeyTypeEnum;
import com.fieldmanagement.fieldmanagement_be.config.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserDetailRepo;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.EmailService;
import com.fieldmanagement.fieldmanagement_be.service.UserService;

import javax.security.sasl.AuthenticationException;

import com.fieldmanagement.fieldmanagement_be.util.OtpGenerator;
import com.fieldmanagement.fieldmanagement_be.util.RedisUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;

    @Override
    public UserModel findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AuthenticationException {
        UserModel userModel = findByEmail(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), userModel.getPassword())) {
            throw new AuthenticationException(loginRequest.getEmail() + loginRequest.getPassword());
        }
        if (!userModel.isActive()) {
            throw new DisabledException(loginRequest.getEmail());
        }
        if (userModel.isLocked()) {
            throw new LockedException(loginRequest.getEmail());
        }

        UserDetailDto userDetailDto = userDetailRepo.findByUserId(userModel.getId())
                .orElseThrow(() -> new UserNotFoundException(userModel.getEmail()));
        TokenDto tokenDto = jwtProvider.generateToken(userModel);

        UserResponse userResponse = userMapper.toResponse(userDetailDto);
        return LoginResponse.builder().accessToken(tokenDto.getAccessToken()).refreshToken(tokenDto.getRefreshToken()).user(userResponse).build();
    }

    @Transactional
    @Override
    public UserResponse register(RegisterRequest registerRequest) throws MessagingException {
        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistsException("Email has existed");
        }

        UserModel userModel = userMapper.toUserModel(registerRequest, passwordEncoder);
        UserModel saveUserModel = userRepo.save(userModel);
        UserDetailModel userDetailModel = userMapper.toUserDetailModel(registerRequest);
        userDetailModel.setUser(saveUserModel);
        UserDetailModel saveUserDetailModel = userDetailRepo.save(userDetailModel);

        UserResponse userResponse = userMapper.toResponse(saveUserDetailModel);

        String otp = OtpGenerator.createOtp();
        String key = RedisUtils.createKey(userModel.getId(), KeyTypeEnum.ACTIVE.value);
        redisTemplate.opsForValue().set(key, otp, KeyTypeEnum.ACTIVE.time, TimeUnit.MINUTES);
        log.info("OTP của user {} : {}", userModel.getEmail(),
                Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
        emailService.sendEmailAsync(userModel.getEmail(), "Xác thực đăng ký tài khoản",
                EmailConstant.OTP_MAIL, Map.of("name", userResponse.getFullName(), "otp", otp));
        return userResponse;
    }
}
