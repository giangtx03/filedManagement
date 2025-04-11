package com.fieldmanagement.fieldmanagement_be.config.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        String errorMessage = "Đăng nhập thất bại";
        if (exception instanceof OAuth2AuthenticationException oAuth2Exception) {
            OAuth2Error error = oAuth2Exception.getError();
            errorMessage = switch (error.getErrorCode()) {
                case "email_not_found" -> oAuth2Exception.getLocalizedMessage();
                default -> "Lỗi không xác định từ OAuth2";
            };
        }
        response.sendRedirect("/login?error=" + URLEncoder.encode(
                errorMessage, StandardCharsets.UTF_8));
    }
}
