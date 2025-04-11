package com.fieldmanagement.fieldmanagement_be.user.exception;

public class UserDoesNotHavePermission extends RuntimeException {
    public UserDoesNotHavePermission(String message) {
        super(message);
    }
}
