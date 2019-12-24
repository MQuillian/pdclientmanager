package com.pdclientmanager.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CaseNumberValidator implements ConstraintValidator<CaseNumberConstraint, String> {
    
    private String regexPattern;
    
    @Override
    public void initialize(CaseNumberConstraint caseNumber) {
        
        // Matches format of ##-CR-####
        this.regexPattern = "\\d{2}J\\d{4}";
    }
    
    @Override
    public boolean isValid(String caseNumber, ConstraintValidatorContext cxt) {
        if(caseNumber.matches(regexPattern)) {
            return true;
        }
        return false;
    }
}
