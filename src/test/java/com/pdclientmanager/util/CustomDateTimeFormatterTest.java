package com.pdclientmanager.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.ChargedCountId;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.validator.DateOpenedConstraint;

public class CustomDateTimeFormatterTest {
    
    

    @ParameterizedTest
    @MethodSource("provideValidDateStrings")
    public void parse_WithValidFormattedString_ShouldReturnLocalDate(String dateInput, boolean expected) throws Exception {
        CustomDateTimeFormatter formatter = new CustomDateTimeFormatter();
        
        boolean testResult = false;
        
        try {
            formatter.parse(dateInput);
            testResult = true;
        } catch(Exception e) {
            // Parser threw exception, so testResult should remain false
        }
        assertThat(testResult).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidDateStrings")
    public void parse_WithInvalidFormattedString_ShouldThrowDateTimeParseException(String dateInput, boolean expected) throws Exception {

        CustomDateTimeFormatter formatter = new CustomDateTimeFormatter();
        
        boolean testResult = false;
        
        try {
            formatter.parse(dateInput);
            // If no exception, testResult should remain false
        } catch(Exception e) {
            // If expected DateTimeParseException is thrown, testResult should be true
            if(e.getClass().equals(DateTimeParseException.class)) {
                testResult = true;
            }
        }
        assertThat(testResult).isEqualTo(expected);
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> provideValidDateStrings() {
        return Stream.of(
                Arguments.of("12/12/2001", true),
                Arguments.of("1/20/2005", true),
                Arguments.of("11/5/1989", true),
                Arguments.of("12/20/05", true),
                Arguments.of("1/20/05", true),
                Arguments.of("01/2/05", true),
                Arguments.of("11-05-2010", true)
                );
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> provideInvalidDateStrings() {
        return Stream.of(
                Arguments.of("", true),
                Arguments.of("letters", true),
                Arguments.of("   ", true),
                Arguments.of("13/15/2000", true),
                Arguments.of("155/20/2000", true),
                Arguments.of("10/200/2001", true),
                Arguments.of("10/5/1", true),
                Arguments.of("10/20/001", true),
                Arguments.of("10/20/20050", true)
                );
    }
}
