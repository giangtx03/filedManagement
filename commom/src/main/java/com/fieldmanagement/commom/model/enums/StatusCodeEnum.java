package com.fieldmanagement.commom.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum StatusCodeEnum {
//  Common
    SERVER_EXCEPTION(1000, "exception.server.error" , HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(1001, "exception.validation.error", HttpStatus.BAD_REQUEST),
    JWT_VERIFICATION_ERROR(1002, "exception.jwt.verification.error", HttpStatus.FORBIDDEN),
//    Auth 11xx
    LOGIN_SUCCESSFULLY(1100, "auth.login.successfully", HttpStatus.ACCEPTED),
    UNAUTHENTICATED(1150, "auth.authenticated.error", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1150, "auth.access.denied", HttpStatus.FORBIDDEN),
//    User 12xx
    USER_LOCKED(1250, "user.locked.error", HttpStatus.BAD_REQUEST),
    USER_UNACTIVE(1251, "user.unactive.error", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1252, "user.not.found", HttpStatus.NOT_FOUND)
//    Booking 13xx
//    Review 14xx
//    Payment 15xx
//    Email 16xx
//    Notification 17xx
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    StatusCodeEnum(int code, String message, HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
    }
}
