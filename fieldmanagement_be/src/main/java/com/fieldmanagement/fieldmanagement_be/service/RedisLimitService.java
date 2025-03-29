package com.fieldmanagement.fieldmanagement_be.service;

public interface RedisLimitService {
    boolean isLoginBlocked(String email);

    void increaseLoginAttempts(String email);

    void resetLoginAttempts(String email);

    boolean isRegisterBlocked(String ip);

    void increaseRegisterAttempts(String ip);

}
