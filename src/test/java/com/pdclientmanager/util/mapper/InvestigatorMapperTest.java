package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Investigator;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class InvestigatorMapperTest {

    @Autowired
    @InjectMocks
    InvestigatorMapper investigatorMapper;
    
    @Mock
    private AttorneyResolver resolver;
    
    private Investigator investigator;
    private InvestigatorForm investigatorFormDto;
    private Attorney attorney;
    
    @BeforeEach
    public void setup() {
        
        MockitoAnnotations.initMocks(this);
        
        attorney = new Attorney.AttorneyBuilder()
                .withId(1L)
                .build();
        
        investigator = new Investigator.InvestigatorBuilder().build();
        investigator.getAssignedAttorneys().add(attorney);
                
        investigatorFormDto = new InvestigatorForm.InvestigatorFormDtoBuilder().build();
        investigatorFormDto.getAssignedAttorneysIds().add(1L);
        
    }
           
    @Test
    public void  mapper_ShouldMapInvestigatorFormDtoToEntity() {
        
        when(resolver.resolve(1L, Attorney.class)).thenReturn(attorney);
        
        Investigator entity = investigatorMapper.toInvestigatorFromInvestigatorForm(investigatorFormDto, new CycleAvoidingMappingContext());
    
        assertThat(entity.getId()).isEqualTo(investigatorFormDto.getId());
        assertThat(entity.getName()).isEqualTo(investigatorFormDto.getName());
        assertThat(entity.getAssignedAttorneys().size()).isEqualTo(investigatorFormDto.getAssignedAttorneysIds().size());
        assertThat(entity.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorFormDto.getAssignedAttorneysIds().get(0));
    }
    
    @Test
    public void mapper_ShouldMapEntityToInvestigatorFormDto() {
        
        when(resolver.toLong(investigator.getAssignedAttorneys().get(0)))
            .thenReturn(1L);
        
        InvestigatorForm formDto = investigatorMapper.toInvestigatorFormFromInvestigator(investigator);
        
        assertThat(formDto.getId()).isEqualTo(investigator.getId());
        assertThat(formDto.getName()).isEqualTo(investigator.getName());
        assertThat(formDto.getAssignedAttorneysIds().size())
            .isEqualTo(investigator.getAssignedAttorneys().size());
        assertThat(formDto.getAssignedAttorneysIds().get(0))
            .isEqualTo(investigator.getAssignedAttorneys().get(0).getId());
    }
}
