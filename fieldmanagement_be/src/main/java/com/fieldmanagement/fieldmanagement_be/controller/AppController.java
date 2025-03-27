package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.commom.model.constant.EmailConstant;
import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.service.EmailService;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import com.fieldmanagement.fieldmanagement_be.util.SecurityUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {
    private final LanguageService languageService;
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping
    public String hello(){

        return languageService.getMessage("hello");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/name")
    public String myName(
    ){
        UserModel userModel = SecurityUtils.getUserFromSecurity();
        return languageService.getMessage("user", StringUtils.hasText(userModel.getEmail()) ? userModel.getEmail() : null);
    }

    @GetMapping("/send-email")
    public ResponseEntity<String> sendEmail(
    ) throws MessagingException {
        Map<String, Object> map = Map.of(
                "name", "Truong Xuan Giang",
                "otp", "123456"
        );
        emailService.sendEmailAsync("gianglvci@gmail.com", "Nguyễn Văn A",EmailConstant.OTP_MAIL ,map);
        return ResponseEntity.ok().body("Gửi email thành công");
    }
}
