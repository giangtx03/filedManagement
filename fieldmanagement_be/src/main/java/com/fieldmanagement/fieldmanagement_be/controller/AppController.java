package com.fieldmanagement.fieldmanagement_be.controller;

import com.fieldmanagement.fieldmanagement_be.config.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {
    private final LanguageService languageService;

    @GetMapping
    public String hello(){

        return languageService.getMessage("hello");
    }

    @GetMapping("/name")
    public String myName(
            @Param("name") String name
    ){
        return languageService.getMessage("user", name);
    }
}
