package com.pdclientmanager.util.validator;

import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.util.CustomDateTimeFormatter;

public class ConsistentDatesValidator implements
        ConstraintValidator<ConsistentDatesConstraint, CaseForm> {
            
    private CustomDateTimeFormatter dateFormatter;

    @Override
    public void initialize(ConsistentDatesConstraint constraintAnnotation) {
        this.dateFormatter = new CustomDateTimeFormatter();
    }

    @Override
    public boolean isValid(CaseForm form, ConstraintValidatorContext context) {

        String dateOpened = form.getDateOpened();
        String dateClosed = form.getDateClosed();
        // If dateClosed isn't empty and is BEFORE dateOpened, returns false
        try {
            if(dateClosed == "") {
                return true;
            } else if(dateFormatter.parse(dateOpened)
                    .isAfter(dateFormatter.parse(dateClosed))) {
                return false;
            }
            return true;
        } catch(DateTimeParseException e) {
            // Leave validation of date format to individual annotations
            return true;
        }
    }
}
