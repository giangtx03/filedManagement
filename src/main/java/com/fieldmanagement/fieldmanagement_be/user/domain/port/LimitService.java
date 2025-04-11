package com.fieldmanagement.fieldmanagement_be.user.domain.port;

public interface LimitService {
    boolean isLoginBlocked(String email);

    void increaseLoginAttempts(String email);

    void resetLoginAttempts(String email);

    boolean isRegisterBlocked(String ip);

    void increaseRegisterAttempts(String ip);

    boolean isRequestBlocked(String ip);

    void increaseRequestAttempts(String ip);
}
