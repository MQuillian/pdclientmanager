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
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;

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
    private InvestigatorDto investigatorDto;
    private InvestigatorFormDto investigatorFormDto;
    private Attorney attorney;
    
    @BeforeEach
    public void setup() {
        
        MockitoAnnotations.initMocks(this);
        
        attorney = new Attorney.AttorneyBuilder()
                .withId(1L)
                .build();
        
        investigator = new Investigator.InvestigatorBuilder().build();
        investigator.getAssignedAttorneys().add(attorney);
        
        investigatorDto = new InvestigatorDto.InvestigatorDtoBuilder().build();
        
        investigatorFormDto = new InvestigatorFormDto.InvestigatorFormDtoBuilder().build();
        investigatorFormDto.getAssignedAttorneyIds().add(1L);
        
    }
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        InvestigatorDto dto = new InvestigatorDto.InvestigatorDtoBuilder()
                .build();
        dto.getAssignedAttorneys().add(new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
            .build());
        
        Investigator entity = investigatorMapper.toInvestigator(dto, new CycleAvoidingMappingContext());
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getEmploymentStatus()).isEqualTo(entity.getEmploymentStatus());
        assertThat(dto.getAssignedAttorneys().get(0).getId())
            .isEqualTo(entity.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        Investigator entity = new Investigator.InvestigatorBuilder()
                .build();
        entity.getAssignedAttorneys().add(new Attorney.AttorneyBuilder().build());
        
        InvestigatorDto dto = investigatorMapper.toInvestigatorDto(entity);
        
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getEmploymentStatus()).isEqualTo(dto.getEmploymentStatus());
        assertThat(entity.getAssignedAttorneys().get(0).getId())
            .isEqualTo(dto.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void  mapper_ShouldMapInvestigatorFormDtoToEntity() {
        
        when(resolver.resolve(1L, Attorney.class)).thenReturn(attorney);
        
        Investigator entity = investigatorMapper.toInvestigatorFromInvestigatorFormDto(investigatorFormDto, new CycleAvoidingMappingContext());
    
        assertThat(entity.getId()).isEqualTo(investigatorFormDto.getId());
        assertThat(entity.getName()).isEqualTo(investigatorFormDto.getName());
        assertThat(entity.getAssignedAttorneys().size()).isEqualTo(investigatorFormDto.getAssignedAttorneyIds().size());
        assertThat(entity.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorFormDto.getAssignedAttorneyIds().get(0));
    }
    
    @Test
    public void mapper_ShouldMapEntityToInvestigatorFormDto() {
        
        when(resolver.toLong(investigator.getAssignedAttorneys().get(0)))
            .thenReturn(1L);
        
        InvestigatorFormDto formDto = investigatorMapper.toInvestigatorFormDtoFromInvestigator(investigator);
        
        assertThat(formDto.getId()).isEqualTo(investigator.getId());
        assertThat(formDto.getName()).isEqualTo(investigator.getName());
        assertThat(formDto.getAssignedAttorneyIds().size())
            .isEqualTo(investigator.getAssignedAttorneys().size());
        assertThat(formDto.getAssignedAttorneyIds().get(0))
            .isEqualTo(investigator.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToInvestigatorMinimalDto() {
        
        InvestigatorMinimalDto mappedMinimalDto = investigatorMapper.toInvestigatorMinimalDtoFromInvestigator(investigator);
        
        assertThat(mappedMinimalDto.getId()).isEqualTo(investigator.getId());
        assertThat(mappedMinimalDto.getName()).isEqualTo(investigator.getName());
        assertThat(mappedMinimalDto.getEmploymentStatus()).isEqualTo(investigator.getEmploymentStatus());
    }
    
    @Test
    public void mapper_ShouldMapInvestigatorDtoToInvestigatorMinimalDto() {
        
        InvestigatorMinimalDto mappedMinimalDto = investigatorMapper.toInvestigatorMinimalDtoFromInvestigatorDto(investigatorDto);
        
        assertThat(mappedMinimalDto.getId()).isEqualTo(investigatorDto.getId());
        assertThat(mappedMinimalDto.getName()).isEqualTo(investigatorDto.getName());
        assertThat(mappedMinimalDto.getEmploymentStatus()).isEqualTo(investigatorDto.getEmploymentStatus());
    }
}
