package com.fieldmanagement.fieldmanagement_be.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.commom.model.builder.ResponseBuilder;
import com.fieldmanagement.commom.model.dto.ResponseDto;
import com.fieldmanagement.commom.model.enums.StatusCodeEnum;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final LanguageService languageService;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto<Void>> handleAuthenticationException(AuthenticationException e) {
        log.error("Login info error: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.UNAUTHENTICATED.getCode(),
                languageService.getMessage(StatusCodeEnum.UNAUTHENTICATED.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.UNAUTHENTICATED.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ResponseDto<Void>> handleLockedException(LockedException e) {
        log.error("Account is locked: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.USER_LOCKED.getCode(),
                languageService.getMessage(StatusCodeEnum.USER_LOCKED.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.USER_LOCKED.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseDto<Void>> handleDisabledException(DisabledException e) {
        log.error("Account is unactive: {}", e.getMessage());

        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.USER_UNACTIVE.getCode(),
                languageService.getMessage(StatusCodeEnum.USER_UNACTIVE.getMessage()));

        return ResponseEntity
                .status(StatusCodeEnum.USER_UNACTIVE.getHttpStatusCode())
                .body(responseDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
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
    @ResponseStatus(HttpStatus.FORBIDDEN)
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
    @ResponseStatus(HttpStatus.FORBIDDEN)
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(error -> {
                            String message = error.getDefaultMessage();
                            return Optional.ofNullable(getLocalizedMessage(message)).orElse(message);
                        }, Collectors.toList())
                ));

        ResponseDto<Map<String, List<String>>> responseDto = ResponseBuilder.errorResponse(
                StatusCodeEnum.VALIDATION_ERROR.getCode(),
                languageService.getMessage(StatusCodeEnum.VALIDATION_ERROR.getMessage()),
                errors
        );

        return ResponseEntity
                .status(StatusCodeEnum.VALIDATION_ERROR.getHttpStatusCode())
                .body(responseDto);
    }

    private String getLocalizedMessage(String message) {
        try {
            String translatedMessage = languageService.getMessage(message);
            return StringUtils.hasText(translatedMessage) ? translatedMessage : null;
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
