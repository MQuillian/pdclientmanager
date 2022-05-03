package com.pdclientmanager.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CaseNumberValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseNumberConstraint {

    String message() default "Invalid case number - Please use ##J#### format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
