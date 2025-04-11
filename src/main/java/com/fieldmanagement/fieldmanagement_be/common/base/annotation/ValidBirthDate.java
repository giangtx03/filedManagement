package com.fieldmanagement.fieldmanagement_be.common.base.annotation;

import com.fieldmanagement.fieldmanagement_be.common.base.validator.BirthDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface ValidBirthDate {
    String message() default "Người dùng phải trên 15 tuổi";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
