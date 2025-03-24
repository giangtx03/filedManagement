package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.fieldmanagement_be.model.dto.TokenDto;
import com.fieldmanagement.fieldmanagement_be.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Auth", description = "Registration login processing controller")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenDto login(@Param("email") String email,
                          @Param("password") String password) throws AuthenticationException {
        return userService.login(email, password);
    }
}
