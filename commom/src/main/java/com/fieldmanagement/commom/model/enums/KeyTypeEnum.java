package com.fieldmanagement.commom.model.enums;

public enum KeyTypeEnum {
    ACTIVE("active", 2),
    RESET_PASSWORD("reset_password", 2),
    LOGIN_FAIL("login_fail", 5),
    REGISTER_LIMIT("register_limit", 60);

    public final long time;
    public final String value;

    KeyTypeEnum(String value, long time) {
        this.value = value;
        this.time = time;
    }
}
