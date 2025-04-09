package com.fieldmanagement.fieldmanagement_be.service;

import com.fieldmanagement.fieldmanagement_be.model.request.auth.LoginRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.RegisterRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.SetPasswordRequest;
import com.fieldmanagement.fieldmanagement_be.model.request.auth.VerifyOtpRequest;
import com.fieldmanagement.fieldmanagement_be.model.response.LoginResponse;
import com.fieldmanagement.fieldmanagement_be.model.response.UserResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import javax.security.sasl.AuthenticationException;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest) throws AuthenticationException;

    UserResponse register(RegisterRequest registerRequest) throws MessagingException;

    void activateAccount(VerifyOtpRequest request);

    void resendEmailActive(String email) throws MessagingException;

    void forgotPassword(@NotBlank(message = "valid.email.notBlank") String email) throws MessagingException;

    String verifyOtpForgotPassword(@Valid VerifyOtpRequest request);

    void setPassword(@Valid SetPasswordRequest request);

    String refreshToken(String refreshToken);

    void logout(String refreshToken);
}
