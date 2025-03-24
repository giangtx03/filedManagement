package com.fieldmanagement.fieldmanagement_be.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final LanguageService languageService;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> handleUserNotFoundException(UserNotFoundException e) {
        log.error("User Not Found Exception: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.USER_NOT_FOUND.getCode(),
                languageService.getMessage(StatusCodeEnum.USER_NOT_FOUND.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.USER_NOT_FOUND.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDto<Void>> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.error("Authorization Denied Exception: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.ACCESS_DENIED.getCode(),
                languageService.getMessage(StatusCodeEnum.ACCESS_DENIED.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.ACCESS_DENIED.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(JWTVerificationException.class)
    ResponseEntity<ResponseDto<Void>> handlerJWTVerificationException(JWTVerificationException e) {

        log.error("JWT Verification Error: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.JWT_VERIFICATION_ERROR.getCode(),
                languageService.getMessage(StatusCodeEnum.JWT_VERIFICATION_ERROR.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.JWT_VERIFICATION_ERROR.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ResponseDto<Void>> handlerException(Exception e) {

        log.error("Exception occurred: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.SERVER_EXCEPTION.getCode(),
                languageService.getMessage(StatusCodeEnum.SERVER_EXCEPTION.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.SERVER_EXCEPTION.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();
            String localizedMessage = StringUtils.hasText(languageService.getMessage(errorMessage))
                    ? languageService.getMessage(errorMessage) : errorMessage;
            errors.computeIfAbsent(field, key -> new ArrayList<>()).add(localizedMessage);
        });

        ResponseDto<Map<String, List<String>>> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.VALIDATION_ERROR.getCode(),
                languageService.getMessage(StatusCodeEnum.VALIDATION_ERROR.getMessage()),
                errors
        );

        return ResponseEntity
                .status(StatusCodeEnum.VALIDATION_ERROR.getHttpStatusCode())
                .body(responseDto);
    }

}
