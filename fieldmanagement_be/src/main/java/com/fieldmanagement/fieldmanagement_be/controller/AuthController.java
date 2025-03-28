package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import com.fieldmanagement.fieldmanagement_be.service.RedisLimitService;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.util.TooManyListenersException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Auth", description = "Registration login processing controller")
public class AuthController {

    private final UserService userService;
    private final LanguageService languageService;
    private final RedisLimitService redisLimitService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@Valid @ParameterObject LoginRequest loginRequest)
    throws AuthenticationException {
        LoginResponse loginResponse = userService.login(loginRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.LOGIN_SUCCESSFULLY;

        ResponseDto<LoginResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                loginResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> register(
            @Valid @ParameterObject RegisterRequest registerRequest,
            HttpServletRequest httpRequest
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = httpRequest.getRemoteAddr();

        if (redisLimitService.isRegisterBlocked(ipAddress)) {
            throw new TooManyListenersException();
        }

        UserResponse userResponse = userService.register(registerRequest);
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
}
