package com.fieldmanagement.fieldmanagement_be.common.base.enums;

public enum KeyTypeEnum {
    ACTIVE("active", 2),
    FORGOT_PASSWORD("forgot_password", 2),
    BLACKLIST_TOKEN("blacklist", 10),
    REQUEST_LIMIT("request_limit", 10),
    LOGIN_FAIL("login_fail", 5),
    REGISTER_LIMIT("register_limit", 60);

    public final long time;
    public final String value;

    KeyTypeEnum(String value, long time) {
        this.value = value;
        this.time = time;
    }
}
