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
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Investigator;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class AttorneyMapperTest {

    @Autowired
    @InjectMocks
    private AttorneyMapper mapper;

    @Mock
    private InvestigatorResolver resolver;
    
    private InvestigatorMinimalDto investigatorMinimalDto;
    private Investigator investigator;
    private Attorney attorney;
    private AttorneyDto attorneyDto;
    private AttorneyFormDto attorneyFormDto;
    
    @BeforeEach
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        
        investigatorMinimalDto = new InvestigatorMinimalDto.InvestigatorMinimalDtoBuilder()
                .build();
        
        attorneyDto = new AttorneyDto.AttorneyDtoBuilder()
                .withName("Test AttorneyDto")
                .withInvestigator(investigatorMinimalDto)
                .build();
        
        attorneyDto.getCaseload().add(new CaseMinimalDto.CaseMinimalDtoBuilder()
                .build());
        
        investigator = new Investigator.InvestigatorBuilder()
                .build();
        
        attorney = new Attorney.AttorneyBuilder()
                .withName("Test Attorney")
                .withInvestigator(investigator)
                .build();
        
        attorney.getCaseload().add(new Case.CaseBuilder()
                .build());
        
        investigator.getAssignedAttorneys().add(attorney);
        
        attorneyFormDto = new AttorneyFormDto.AttorneyFormDtoBuilder()
                .build();
    }
        
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        attorneyDto.getCaseload().add(new CaseMinimalDto.CaseMinimalDtoBuilder()
                .build());
        
        Attorney entity = mapper.toAttorney(attorneyDto, new CycleAvoidingMappingContext());
        
        assertThat(attorneyDto.getId()).isEqualTo(entity.getId());
        assertThat(attorneyDto.getName()).isEqualTo(entity.getName());
        assertThat(attorneyDto.getEmploymentStatus()).isEqualTo(entity.getEmploymentStatus());
        assertThat(attorneyDto.getInvestigator().getId()).isEqualTo(entity.getInvestigator().getId());
        assertThat(attorneyDto.getInvestigator().getName()).isEqualTo(entity.getInvestigator().getName());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        attorney.getCaseload().add(new Case.CaseBuilder().build());
        investigator.getAssignedAttorneys().add(attorney);
        
        AttorneyDto dto = mapper.toAttorneyDto(attorney);
        
        assertThat(attorney.getId()).isEqualTo(dto.getId());
        assertThat(attorney.getName()).isEqualTo(dto.getName());
        assertThat(attorney.getEmploymentStatus()).isEqualTo(dto.getEmploymentStatus());
        assertThat(attorney.getInvestigator().getId()).isEqualTo(dto.getInvestigator().getId());
        assertThat(attorney.getInvestigator().getName()).isEqualTo(dto.getInvestigator().getName());
    }
    
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
        
        AttorneyFormDto mappedFormDto = mapper.toAttorneyFormDtoFromAttorney(attorney);
        
        assertThat(mappedFormDto.getId()).isEqualTo(attorney.getId());
        assertThat(mappedFormDto.getName()).isEqualTo(attorney.getName());
        assertThat(mappedFormDto.getInvestigatorId()).isEqualTo(attorney.getInvestigator().getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToAttorneyMinimalDto() {
        
        AttorneyMinimalDto mappedMinimalDto = mapper.toAttorneyMinimalDtoFromAttorney(attorney);
        
        assertThat(mappedMinimalDto.getId()).isEqualTo(attorney.getId());
        assertThat(mappedMinimalDto.getName()).isEqualTo(attorney.getName());
    }
    
    @Test
    public void mapper_ShouldMapAttorneyDtoToAttorneyMinimalDto() {
        
        AttorneyMinimalDto mappedMinimalDto = mapper.toAttorneyMinimalDtoFromAttorneyDto(attorneyDto);
        
        assertThat(mappedMinimalDto.getId()).isEqualTo(attorneyDto.getId());
        assertThat(mappedMinimalDto.getName()).isEqualTo(attorneyDto.getName());
    }
}
