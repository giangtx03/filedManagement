package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Auth", description = "Registration login processing controller")
public class AuthController {

    private final UserService userService;
    private final LanguageService languageService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ParameterObject LoginRequest loginRequest)
    throws AuthenticationException {
        LoginResponse loginResponse = userService.login(loginRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.LOGIN_SUCCESSFULLY;

        ResponseDto<LoginResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.getCode(),
                languageService.getMessage(statusCodeEnum.getMessage()),
                loginResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.getHttpStatusCode())
                .body(responseDto);
    }
}
