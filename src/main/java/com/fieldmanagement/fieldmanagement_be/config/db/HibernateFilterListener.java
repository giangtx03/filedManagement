package com.fieldmanagement.fieldmanagement_be.config.db;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class HibernateFilterListener {
    private final FilterInterceptor filterInterceptor;

    @Before("execution(* com.fieldmanagement..db.impl..*(..))")
    public void applyFilter() {
        filterInterceptor.enableFilter();
    }
}
