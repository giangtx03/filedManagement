package com.fieldmanagement.fieldmanagement_be.service;

import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.model.request.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.VerifyOtpRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import jakarta.mail.MessagingException;

import javax.security.sasl.AuthenticationException;

public interface UserService {
    UserModel findByEmail(String email);
    LoginResponse login(LoginRequest loginRequest) throws AuthenticationException;
    UserResponse register(RegisterRequest registerRequest) throws MessagingException;
    void activateAccount(VerifyOtpRequest request);
}
