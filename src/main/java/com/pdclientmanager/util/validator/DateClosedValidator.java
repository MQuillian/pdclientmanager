package com.pdclientmanager.util.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pdclientmanager.util.CustomDateTimeFormatter;

public class DateClosedValidator implements ConstraintValidator<DateClosedConstraint, String> {
    
    private CustomDateTimeFormatter dateFormatter;
    
    @Override
    public void initialize(DateClosedConstraint date) {
        this.dateFormatter = new CustomDateTimeFormatter();
        }
    
    // Returns true for empty string (e.g. case is open) or valid date format
    
    @Override
    public boolean isValid(String date, ConstraintValidatorContext cxt) {
        if(date == "") {
            return true;
        } else {
            try {
                if(dateFormatter.parse(date).isAfter(LocalDate.now())) {
                    return false;
                }
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }

}
