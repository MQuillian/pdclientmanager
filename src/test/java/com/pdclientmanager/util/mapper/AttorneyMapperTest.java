package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Investigator;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class AttorneyMapperTest {

    @Autowired
    @InjectMocks
    private AttorneyMapper mapper;

    @Mock
    private InvestigatorResolver resolver;
    
    private Investigator investigator;
    private Attorney attorney;
    private AttorneyForm attorneyFormDto;
    
    @BeforeEach
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);

        
        investigator = new Investigator.InvestigatorBuilder()
                .build();
        
        attorney = new Attorney.AttorneyBuilder()
                .withName("Test Attorney")
                .withInvestigator(investigator)
                .build();
        
        attorney.getCaseload().add(new Case.CaseBuilder()
                .build());
        
        investigator.getAssignedAttorneys().add(attorney);
        
        attorneyFormDto = new AttorneyForm.AttorneyFormDtoBuilder()
                .build();
    }
        
    // Mapping between AttorneyFormDto and Attorney entity
    
    @Test
    public void mapper_ShouldMapAttorneyFormDtoToEntity() {
        
        Mockito.when(resolver.resolve(1L, Investigator.class)).thenReturn(investigator);
        
        Attorney mappedAttorney = mapper.toAttorneyFromAttorneyFormDto(attorneyFormDto, new CycleAvoidingMappingContext());

        assertThat(mappedAttorney.getId()).isEqualTo(attorneyFormDto.getId());
        assertThat(mappedAttorney.getName()).isEqualTo(attorneyFormDto.getName());
        assertThat(mappedAttorney.getInvestigator().getId()).isEqualTo(attorneyFormDto.getInvestigatorId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToAttorneyFormDto() {
        
        Mockito.when(resolver.toLong(attorney.getInvestigator())).thenReturn(attorney.getInvestigator().getId());
        
        AttorneyForm mappedFormDto = mapper.toAttorneyFormDtoFromAttorney(attorney);
        
        assertThat(mappedFormDto.getId()).isEqualTo(attorney.getId());
        assertThat(mappedFormDto.getName()).isEqualTo(attorney.getName());
        assertThat(mappedFormDto.getInvestigatorId()).isEqualTo(attorney.getInvestigator().getId());
    }
}
