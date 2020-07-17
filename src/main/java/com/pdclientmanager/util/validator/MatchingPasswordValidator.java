package com.pdclientmanager.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pdclientmanager.model.form.UserForm;

public class MatchingPasswordValidator implements ConstraintValidator<MatchingPasswordConstraint, Object> {

    @Override
    public void initialize(MatchingPasswordConstraint constraintAnnotation) {}
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        UserForm user = (UserForm) obj;
        return user.getPassword().equals(user.getMatchingPassword());    
    } 
}
