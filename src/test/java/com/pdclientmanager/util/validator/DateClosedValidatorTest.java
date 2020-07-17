package com.pdclientmanager.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class DateClosedValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @AfterAll
    public static void close() {
        validatorFactory.close();
    }
    
    @ParameterizedTest
    @MethodSource("provideDateStrings")
    public void shouldReturnFalseForInvalidDate(String dateInput, boolean expected) {
        
        boolean validDate = true;
        CaseForm testDto = new CaseForm.CaseFormDtoBuilder()
                .withDateClosed(dateInput).build();
        
        Set<ConstraintViolation<CaseForm>> violations = 
                validator.validate(testDto);
        
        for(ConstraintViolation<CaseForm> violation : violations) {
            if(violation.getConstraintDescriptor().getAnnotation().annotationType()
                    .equals(DateClosedConstraint.class)) {
                validDate = false;
            }
        }
        
        assertThat(validDate).isEqualTo(expected);
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> provideDateStrings() {
        return Stream.of(
                Arguments.of("letters", false),
                Arguments.of("   ", false),
                Arguments.of("11-05-2010", false),
                Arguments.of("13/15/2000", false),
                Arguments.of("155/20/2000", false),
                Arguments.of("10/200/2001", false),
                Arguments.of("10/5/1", false),
                Arguments.of("10/20/001", false),
                Arguments.of("10/20/20050", false),
                Arguments.of("10/20/2100", false),
                Arguments.of("", true),
                Arguments.of("10/10/2000", true),
                Arguments.of("1/20/2005", true),
                Arguments.of("11/5/1989", true),
                Arguments.of("1/4/1986", true),
                Arguments.of("12/20/05", true),
                Arguments.of("1/20/87", true),
                Arguments.of("05/5/95", true),
                Arguments.of("4/6/04", true)
                );
    }
    
}
