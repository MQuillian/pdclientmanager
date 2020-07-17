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
public class CaseNumberValidatorTest {
    
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeAll
    public static void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @AfterAll
    public static void close() {
        validatorFactory.close();
    }
    
    @ParameterizedTest
    @MethodSource("provideCaseNumberStrings")
    public void shouldReturnFalseForInvalidCaseNumber(String caseNumber, boolean expected) {
        
        boolean validCaseNumber = true;
        CaseForm testDto = new CaseForm.CaseFormDtoBuilder()
                .withCaseNumber(caseNumber).build();
        
        Set<ConstraintViolation<CaseForm>> violations = validator.validate(testDto);
        
        for(ConstraintViolation<CaseForm> violation : violations) {
            if(violation.getConstraintDescriptor().getAnnotation().annotationType()
                    .equals(CaseNumberConstraint.class)) {
                validCaseNumber = false;
            }
        }
        
        assertThat(validCaseNumber).isEqualTo(expected);
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> provideCaseNumberStrings() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("    ", false),
                Arguments.of("letters", false),
                Arguments.of("1001345", false),
                Arguments.of("01j5678", false),
                Arguments.of("101J5678", false),
                Arguments.of("11J57890", false),
                Arguments.of("00J0000", true),
                Arguments.of("19J9999", true)
                );
    }
}
