package com.fieldmanagement.fieldmanagement_be.user.usecase;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.User;
import com.fieldmanagement.fieldmanagement_be.user.domain.model.UserDetail;
import com.fieldmanagement.fieldmanagement_be.user.exception.EmailExistsException;
import com.fieldmanagement.fieldmanagement_be.common.exception.OtpInvalidException;
import com.fieldmanagement.fieldmanagement_be.user.exception.UserIsAvtiveException;
import com.fieldmanagement.fieldmanagement_be.user.exception.UserNotFoundException;
import com.fieldmanagement.fieldmanagement_be.common.base.constant.EmailConstant;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.KeyTypeEnum;
import com.fieldmanagement.fieldmanagement_be.common.port.MailSender;
import com.fieldmanagement.fieldmanagement_be.common.util.OtpGenerator;
import com.fieldmanagement.fieldmanagement_be.common.util.RedisUtils;
import com.fieldmanagement.fieldmanagement_be.config.security.UserDetailsImpl;
import com.fieldmanagement.fieldmanagement_be.infra.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.user.adapter.mapper.UserMapper;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.auth.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.auth.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.auth.SetPasswordRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.request.auth.VerifyOtpRequest;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.user.adapter.web.dto.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.user.domain.port.UserRepository;
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

import javax.security.sasl.AuthenticationException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final UserRepository userRepository;

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MailSender mailSender;
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

    public LoginResponse login(LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User userModel = userDetails.getUser();

        TokenDto tokenDto = jwtProvider.generateToken(userModel);

        UserResponse userResponse = userMapper.toResponse(userModel);
        return LoginResponse.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .user(userResponse)
                .build();
    }

    @Transactional
    public UserResponse register(RegisterRequest registerRequest) throws MessagingException {
        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistsException("Email has existed");
        }

        User user = userMapper.toUser(registerRequest, passwordEncoder);
        UserDetail userDetail = userMapper.toUserDetail(registerRequest);
        user.setUserDetail(userDetail);
        User saved = userRepo.save(user);
        UserResponse userResponse = userMapper.toResponse(saved);

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.ACTIVE, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Xác thực đăng ký tài khoản",
                EmailConstant.OTP_MAIL, Map.of("name", userResponse.getFullName(), "otp", otp));
        return userResponse;
    }

    @Transactional
    public void activateAccount(VerifyOtpRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.ACTIVE.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));
        user.setActive(true);
        userRepo.save(user);

        redisTemplate.delete(key);
    }

    public void resendEmailActive(String email) throws MessagingException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        if (user.isActive()) {
            throw new UserIsAvtiveException(email);
        }

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.ACTIVE, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Xác thực tài khoản",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", user.getUserDetail().getFullName(),
                        "otp", otp));
    }

    public void forgotPassword(String email) throws MessagingException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.FORGOT_PASSWORD, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Đặt lại mật khẩu",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", user.getUserDetail().getFullName(),
                        "otp", otp));
    }

    public String verifyOtpForgotPassword(VerifyOtpRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        String key = RedisUtils.createKey(KeyTypeEnum.FORGOT_PASSWORD.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }
        redisTemplate.delete(key);
        return jwtProvider.generateToken(user, resetPasswordKey, timeResetPassword);
    }

    @Transactional
    public void setPassword(SetPasswordRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, request.getToken());
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid token set password");
        }
        DecodedJWT decodeToken = jwtProvider.decodeToken(request.getToken(), resetPasswordKey);
        String email = decodeToken.getSubject();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        redisTemplate.opsForValue().set(key, "invalid",
                KeyTypeEnum.BLACKLIST_TOKEN.time, TimeUnit.MINUTES);
    }

    public String refreshToken(String refreshToken) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, refreshToken);
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid refresh token");
        }

        DecodedJWT decodeToken = jwtProvider.decodeToken(refreshToken, refreshKey);
        String email = decodeToken.getSubject();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        return jwtProvider.generateToken(user, accessKey, timeAccess);
    }

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
