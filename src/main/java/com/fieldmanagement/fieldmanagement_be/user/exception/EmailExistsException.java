package com.fieldmanagement.fieldmanagement_be.user.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
