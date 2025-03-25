package com.fieldmanagement.fieldmanagement_be.service;

import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;

import javax.security.sasl.AuthenticationException;

public interface UserService {
    UserModel findByEmail(String email);
    LoginResponse login(LoginRequest loginRequest) throws AuthenticationException;
}
