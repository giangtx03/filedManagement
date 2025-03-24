package com.fieldmanagement.fieldmanagement_be.service;

import com.fieldmanagement.fieldmanagement_be.model.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;

import javax.security.sasl.AuthenticationException;

public interface UserService {
    UserModel findByEmail(String email);
    TokenDto login(String email, String password) throws AuthenticationException;
}
