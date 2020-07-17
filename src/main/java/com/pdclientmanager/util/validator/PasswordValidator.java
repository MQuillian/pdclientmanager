package com.pdclientmanager.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password.length() < 5 || password.length() > 20) {
            return false;
        }
        return true;
    }
}
