package com.pdclientmanager.util.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateOpenedValidator implements ConstraintValidator<DateOpenedConstraint, String> {
    
    private DateTimeFormatter dateFormatter;
    
    @Override
    public void initialize(DateOpenedConstraint date) {
        this.dateFormatter = DateTimeFormatter
                .ofPattern("[MM/dd/yyyy][M/dd/yyyy][MM/d/yyyy][M/d/yyyy][MM/dd/yy][M/dd/yy][MM/d/yy][M/d/yy]");
    }
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext cxt) {
        try {
            if(LocalDate.parse(date, dateFormatter).isAfter(LocalDate.now())) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
