package com.pdclientmanager.util;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;

public class CustomDateTimeFormatter {

    private DateTimeFormatter customFormatter;
    private String acceptedFormats = "[MM/dd/][M/dd/][MM/d/][M/d/][MM-dd-][M-dd-][MM-d-][M-d-]";
    
    public CustomDateTimeFormatter() {
        this.customFormatter = new DateTimeFormatterBuilder()
                .appendPattern(acceptedFormats)
                .appendValueReduced(
                        ChronoField.YEAR, 2, 4, Year.now().getValue() - 80)
                .toFormatter();
    }
    
    
    /* Checks date matches basic MONTH/DAY/YEAR or MONTH-DAY-YEAR pattern (allows for 1-2 digit months
     *  and days, and 2 or 4 digit years) and if date matches, calls LocalDate.parse
     */
    
    public LocalDate parse(String date) throws DateTimeParseException {
        if(date.matches("\\d{1,2}(/|-)\\d{1,2}\\1(\\d{2}|\\d{4})")) {
            return LocalDate.parse(date, this.customFormatter);
        } else {
            throw new DateTimeParseException(
                    "Date must be in MM/DD/YY or MM-DD-YY format", date, 0);
        }
    }
    
    public String toFormattedDateString(LocalDate date) {
        if(date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }
}
