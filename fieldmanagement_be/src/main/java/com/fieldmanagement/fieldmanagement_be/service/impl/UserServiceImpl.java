package com.fieldmanagement.fieldmanagement_be.service.impl;

import com.fieldmanagement.commom.exception.UserNotFoundException;
import com.fieldmanagement.fieldmanagement_be.config.jwt.JwtProvider;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.dao.repository.UserRepo;
import com.fieldmanagement.fieldmanagement_be.model.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final LanguageService languageService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
    }

    @Override
    public TokenDto login(String email, String password) {
        try {
            UserModel userModel = findByEmail(email);

            if (userModel.getIsLocked()) {
                throw new LockedException("Tài khoản đã bị khóa");
            }
            if (!userModel.getIsActive()) {
                throw new DisabledException("Tài khoản chưa được kích hoạt");
            }

            if (!passwordEncoder.matches(password, userModel.getPassword())) {
                throw new AuthenticationException("Sai thông tin tài khoản");
            }

            return jwtProvider.generateToken(userModel);
        }catch (AuthenticationException e){
            log.warn("sai thông tin tài khoản");
        }
        return null;
    }
}
