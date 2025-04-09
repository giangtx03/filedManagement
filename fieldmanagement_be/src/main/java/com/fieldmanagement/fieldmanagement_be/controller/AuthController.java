package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.ProviderEnum;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.SetPasswordRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.VerifyOtpRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.RedisLimitService;
import com.fieldmanagement.fieldmanagement_be.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.TooManyListenersException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Auth", description = "Registration login processing controller")
public class AuthController {

    private final AuthService authService;
    private final LanguageService languageService;
    private final RedisLimitService redisLimitService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(
            @Valid @ParameterObject LoginRequest loginRequest
    ) throws AuthenticationException, TooManyListenersException {
        try {
            if (redisLimitService.isLoginBlocked(loginRequest.getEmail())) {
                throw new TooManyListenersException("5");
            }

            LoginResponse loginResponse = authService.login(loginRequest);
            redisLimitService.resetLoginAttempts(loginRequest.getEmail());

            StatusCodeEnum statusCodeEnum = StatusCodeEnum.LOGIN_SUCCESSFULLY;

            ResponseDto<LoginResponse> responseDto = ResponseBuilder.okResponse(
                    statusCodeEnum.code,
                    languageService.getMessage(statusCodeEnum.message),
                    loginResponse
            );
            return ResponseEntity
                    .status(statusCodeEnum.httpStatusCode)
                    .body(responseDto);
        } catch (Exception e) {
            redisLimitService.increaseLoginAttempts(loginRequest.getEmail());
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> register(
            @Valid @ParameterObject RegisterRequest registerRequest,
            HttpServletRequest httpRequest
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = httpRequest.getRemoteAddr();

        if (redisLimitService.isRegisterBlocked(ipAddress)) {
            throw new TooManyListenersException("60");
        }

        UserResponse userResponse = authService.register(registerRequest);
        redisLimitService.increaseRegisterAttempts(ipAddress);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REGISTER_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/verify-activation")
    public ResponseEntity<ResponseDto<Void>> verifyActivation(
            @Valid @ParameterObject VerifyOtpRequest request
    ) {
        authService.activateAccount(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.ACTIVE_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/resend-otp-activation")
    public ResponseEntity<ResponseDto<Void>> resendOtpActivation(
            @NotBlank(message = "valid.email.notBlank") @ParameterObject String email,
            HttpServletRequest request
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = request.getRemoteAddr();
        if (redisLimitService.isRequestBlocked(ipAddress)) {
            throw new TooManyListenersException("10");
        }

        authService.resendEmailActive(email);
        redisLimitService.increaseRequestAttempts(ipAddress);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.SEND_OTP_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto<Void>> forgotPassword(
            @NotBlank(message = "valid.email.notBlank") @ParameterObject String email,
            HttpServletRequest request
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = request.getRemoteAddr();
        if (redisLimitService.isRequestBlocked(ipAddress)) {
            throw new TooManyListenersException("10");
        }
        authService.forgotPassword(email);
        redisLimitService.increaseRequestAttempts(ipAddress);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.SEND_OTP_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/verify-otp-forgot-password")
    public ResponseEntity<ResponseDto<String>> verifyOtpForgotPassword(
            @Valid @ParameterObject VerifyOtpRequest request
    ) {
        String token = authService.verifyOtpForgotPassword(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.OTP_VALID;

        ResponseDto<String> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                token
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/set-password")
    public ResponseEntity<ResponseDto<Void>> setPassword(
            @Valid @ParameterObject SetPasswordRequest request
    ) {
        authService.setPassword(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.CHANGE_PASSWORD_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto<String>> refreshToken(
            @NotBlank @RequestHeader("RefreshToken") String refreshToken
    ) {
        String accessToken = authService.refreshToken(refreshToken);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<String> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                accessToken
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/social-login/{provider}")
    public void getOAuth2LoginUrl(
            @PathVariable String provider, HttpServletResponse response
    ) throws IOException {
        if (!ProviderEnum.isValid(provider)) {
            throw new OAuth2AuthenticationException("Invalid provider: " + provider);
        }

        String redirectUri = "/oauth2/authorization/" + provider.toLowerCase();
        response.sendRedirect(redirectUri);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(
            @NotBlank @RequestHeader("RefreshToken") String refreshToken
    ) {
        authService.logout(refreshToken);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }
}
