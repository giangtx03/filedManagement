package com.fieldmanagement.fieldmanagement_be.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {
    private final MessageSource messageSource;

    @GetMapping
    public String hello(){

        return messageSource.getMessage("hello", null, LocaleContextHolder.getLocale());
    }
}
