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
    UNAUTHENTICATED(1101, "auth.authenticated.error", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1102, "auth.access.denied", HttpStatus.FORBIDDEN),
//    User 12xx
    USER_LOCKED(1201, "user.locked.error", HttpStatus.BAD_REQUEST),
    USER_UNACTIVE(1202, "user.unactive.error", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1203, "user.not.found", HttpStatus.NOT_FOUND)
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
