package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class InvestigatorMapperTest {

    @Autowired
    InvestigatorMapper investigatorMapper;
    
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
        
        InvestigatorDto dto = investigatorMapper.toInvestigatorDto(entity, new CycleAvoidingMappingContext());
        
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getEmploymentStatus()).isEqualTo(dto.getEmploymentStatus());
        assertThat(entity.getAssignedAttorneys().get(0).getId())
            .isEqualTo(dto.getAssignedAttorneys().get(0).getId());
    }
}
