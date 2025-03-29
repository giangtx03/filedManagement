package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.fieldmanagement.commom.model.enums.KeyTypeEnum;
import com.fieldmanagement.fieldmanagement_be.service.RedisLimitService;
import com.fieldmanagement.fieldmanagement_be.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisLimitServiceImpl implements RedisLimitService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${login.max.attempts}")
    private int loginMaxAttempts;
    @Value("${register.max.attempts}")
    private int registerMaxAttempts;

    @Override
    public boolean isLoginBlocked(String email) {
        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object attemptsObj = ops.get(key);
        if (attemptsObj != null) {
            int attempts = (int) attemptsObj;
            return attempts >= loginMaxAttempts;
        }
        return false;
    }

    @Override
    public void increaseLoginAttempts(String email) {
        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Integer attempts = (Integer) ops.get(key);
        if (attempts == null) {
            ops.set(key, 1, KeyTypeEnum.LOGIN_FAIL.time, TimeUnit.MINUTES);
        } else {
            ops.set(key, attempts + 1, KeyTypeEnum.LOGIN_FAIL.time, TimeUnit.MINUTES);
        }
    }

    @Override
    public void resetLoginAttempts(String email) {
        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
        redisTemplate.delete(key);
    }

    @Override
    public boolean isRegisterBlocked(String ip) {
        String key = RedisUtils.createKey(KeyTypeEnum.REGISTER_LIMIT.value, ip);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object attemptsObj = ops.get(key);
        if (attemptsObj != null) {
            int attempts = (int) attemptsObj;
            return attempts >= registerMaxAttempts;
        }
        return false;
    }

    @Override
    public void increaseRegisterAttempts(String ip) {
        String key = RedisUtils.createKey(KeyTypeEnum.REGISTER_LIMIT.value, ip);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Integer attempts = (Integer) ops.get(key);
        if (attempts == null) {
            ops.set(key, 1, KeyTypeEnum.REGISTER_LIMIT.time, TimeUnit.MINUTES);
        } else {
            ops.set(key, attempts + 1, KeyTypeEnum.REGISTER_LIMIT.time, TimeUnit.MINUTES);
        }
    }
}
