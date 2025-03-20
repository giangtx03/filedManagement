package com.fieldmanagement.commom.exception;

public class UserDoesNotHavePermission extends RuntimeException {
    public UserDoesNotHavePermission(String message) {
        super(message);
    }
}
