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
import com.pdclientmanager.model.dto.CaseFormDto;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ConsistentDatesValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeAll
    public static void createValidator() throws NoSuchMethodException {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @ParameterizedTest
    @MethodSource("provideDatePairStrings")
    public void shouldReturnFalseWhenDateOpenedAfterDateClosed(
            String dateOpened, String dateClosed, boolean expected) {
        
        boolean consistentDates = true;
        CaseFormDto testForm = new CaseFormDto.CaseFormDtoBuilder()
                .withDateOpened(dateOpened)
                .withDateClosed(dateClosed)
                .build();
        
        Set<ConstraintViolation<CaseFormDto>> violations =
                validator.validate(testForm);
        
        for(ConstraintViolation<CaseFormDto> violation : violations) {
            if(violation.getConstraintDescriptor().getAnnotation().annotationType()
                .equals(ConsistentDatesConstraint.class)) {
                    consistentDates = false;
                }
        }
        
        assertThat(consistentDates).isEqualTo(expected);
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> provideDatePairStrings() {
        return Stream.of(
                Arguments.of("1/2/2010", "1/1/2010", false),
                Arguments.of("10/10/2000", "10/10/1999", false),
                Arguments.of("1/1/2000", "1/1/2000", true),
                Arguments.of("10/10/2000", "10/12/2000", true),
                Arguments.of("1/1/2001", "1/1/2002", true)
                );
    }
}
