package com.fieldmanagement.fieldmanagement_be.config.language;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final MessageSource messageSource;

    public String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String messageCode, Object ...args){
        if (args == null || Arrays.stream(args).allMatch(Objects::isNull)) {
            args = new Object[]{""};
        }
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }
}
