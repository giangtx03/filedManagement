package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fieldmanagement.commom.exception.EmailExistsException;
import com.fieldmanagement.commom.exception.OtpInvalidException;
import com.fieldmanagement.commom.exception.UserIsAvtiveException;
import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.commom.model.constant.EmailConstant;
import com.fieldmanagement.commom.model.enums.KeyTypeEnum;
import com.fieldmanagement.fieldmanagement_be.config.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.config.security.UserDetailsImpl;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserDetailRepo;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.model.dto.UserDetailDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserDetailModel;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.SetPasswordRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.VerifyOtpRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.EmailService;
import com.fieldmanagement.fieldmanagement_be.service.AuthService;

import javax.security.sasl.AuthenticationException;

import com.fieldmanagement.fieldmanagement_be.util.OtpGenerator;
import com.fieldmanagement.fieldmanagement_be.util.RedisUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.accessKey}")
    private String accessKey;
    @Value("${jwt.refreshKey}")
    private String refreshKey;
    @Value("${jwt.resetPasswordKey}")
    private String resetPasswordKey;
    @Value("${jwt.expiration.time.resetPassword}")
    private long timeResetPassword;
    @Value("${jwt.expiration.time.access}")
    private long timeAccess;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userModel = userDetails.getUser();

        UserDetailDto userDetailDto = userDetailRepo.findByUserId(userModel.getId())
                .orElseThrow(() -> new UserNotFoundException(userModel.getEmail()));
        TokenDto tokenDto = jwtProvider.generateToken(userModel);

        UserResponse userResponse = userMapper.toResponse(userDetailDto);
        return LoginResponse.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .user(userResponse)
                .build();
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
        saveOtp(KeyTypeEnum.ACTIVE, userModel.getEmail(), otp);
        emailService.sendEmailAsync(userModel.getEmail(), "Xác thực đăng ký tài khoản",
                EmailConstant.OTP_MAIL, Map.of("name", userResponse.getFullName(), "otp", otp));
        return userResponse;
    }

    @Transactional
    @Override
    public void activateAccount(VerifyOtpRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.ACTIVE.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }

        UserModel userModel = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found !!!"));
        userModel.setActive(true);
        userRepo.save(userModel);

        redisTemplate.delete(key);
    }

    @Override
    public void resendEmailActive(String email) throws MessagingException {
        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email không tồn tại."));

        if (userModel.isActive()) {
            throw new UserIsAvtiveException(email);
        }

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.ACTIVE, userModel.getEmail(), otp);
        emailService.sendEmailAsync(userModel.getEmail(), "Xác thực tài khoản",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", userModel.getUserDetail().getFullName(),
                        "otp", otp));
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email không tồn tại."));

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.FORGOT_PASSWORD, userModel.getEmail(), otp);
        emailService.sendEmailAsync(userModel.getEmail(), "Đặt lại mật khẩu",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", userModel.getUserDetail().getFullName(),
                        "otp", otp));
    }

    @Override
    public String verifyOtpForgotPassword(VerifyOtpRequest request) {
        UserModel userModel = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email không tồn tại."));

        String key = RedisUtils.createKey(KeyTypeEnum.FORGOT_PASSWORD.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }
        redisTemplate.delete(key);
        return jwtProvider.generateToken(userModel, resetPasswordKey, timeResetPassword);
    }

    @Transactional
    @Override
    public void setPassword(SetPasswordRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, request.getToken());
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid token set password");
        }
        DecodedJWT decodeToken = jwtProvider.decodeToken(request.getToken(), resetPasswordKey);
        String email = decodeToken.getSubject();

        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email không tồn tại."));

        userModel.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(userModel);
        redisTemplate.opsForValue().set(key, "invalid",
                KeyTypeEnum.BLACKLIST_TOKEN.time, TimeUnit.MINUTES);
    }

    @Override
    public String refreshToken(String refreshToken) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, refreshToken);
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid refresh token");
        }

        DecodedJWT decodeToken = jwtProvider.decodeToken(refreshToken, refreshKey);
        String email = decodeToken.getSubject();

        UserModel userModel = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email không tồn tại."));

        return jwtProvider.generateToken(userModel, accessKey, timeAccess);
    }

    @Override
    public void logout(String refreshToken) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, refreshToken);
        redisTemplate.opsForValue().set(key, "invalid",
                KeyTypeEnum.BLACKLIST_TOKEN.time, TimeUnit.MINUTES);

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private void saveOtp(KeyTypeEnum keyTypeEnum, String subKey, String otp) {
        String key = RedisUtils.createKey(keyTypeEnum.value, subKey);
        redisTemplate.opsForValue().set(key, otp, keyTypeEnum.time, TimeUnit.MINUTES);
        log.info("OTP của user {} : {}", subKey,
                Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
    }

    private boolean validateOtp(String key, String otp) {
        Object storedOtp = redisTemplate.opsForValue().get(key);
        return storedOtp != null && otp.equals(storedOtp.toString());
    }
}
