package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import com.fieldmanagement.fieldmanagement_be.model.entity.UserModel;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import com.fieldmanagement.fieldmanagement_be.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {
    private final LanguageService languageService;
    private final UserService userService;

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
}
