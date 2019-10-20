package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Client;
import com.pdclientmanager.model.entity.Judge;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class CaseMapperTest {

    private CaseDto caseDto;
    private Case courtCase;
    
    @Autowired
    CaseMapper caseMapper;
    
    @BeforeEach
    public void setUp() {
        
        caseDto = new CaseDto.CaseDtoBuilder()
            .withAttorney(new AttorneyMinimalDto.AttorneyMinimalDtoBuilder().build())
            .withClient(new ClientMinimalDto.ClientMinimalDtoBuilder().build())
            .withJudge(new JudgeDto.JudgeDtoBuilder().build())
            .build();
        ChargedCountDto testCountDto = new ChargedCountDto.ChargedCountDtoBuilder().build();
        caseDto.addChargedCount(testCountDto);
        
        courtCase = new Case.CaseBuilder()
            .withAttorney(new Attorney.AttorneyBuilder().build())
            .withClient(new Client.ClientBuilder().build())
            .withJudge(new Judge.JudgeBuilder().build())
            .build();
        ChargedCount testCount = new ChargedCount.ChargedCountBuilder()
            .build();
        courtCase.addChargedCount(testCount);
    }
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        Case entity = caseMapper.toCase(caseDto, new CycleAvoidingMappingContext());
        
        assertThat(caseDto.getId()).isEqualTo(entity.getId());
        assertThat(caseDto.getCaseNumber()).isEqualTo(entity.getCaseNumber());
        assertThat(caseDto.getCaseStatus()).isEqualTo(entity.getCaseStatus());
        assertThat(caseDto.getDateOpened()).isEqualTo(entity.getDateOpened());
        assertThat(caseDto.getDateClosed()).isEqualTo(entity.getDateClosed());
        assertThat(caseDto.getClient().getId()).isEqualTo(entity.getClient().getId());
        assertThat(caseDto.getJudge().getId()).isEqualTo(entity.getJudge().getId());
        assertThat(caseDto.getAttorney().getId()).isEqualTo(entity.getAttorney().getId());
        assertThat(caseDto.getChargedCounts().size())
            .isEqualTo(entity.getChargedCounts().size());
        assertThat(caseDto.getChargedCounts().get(1).getId())
            .isEqualTo(entity.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
                
        CaseDto dto = caseMapper.toCaseDto(courtCase);
                
        assertThat(courtCase.getId()).isEqualTo(dto.getId());
        assertThat(courtCase.getCaseNumber()).isEqualTo(dto.getCaseNumber());
        assertThat(courtCase.getCaseStatus()).isEqualTo(dto.getCaseStatus());
        assertThat(courtCase.getDateOpened()).isEqualTo(dto.getDateOpened());
        assertThat(courtCase.getDateClosed()).isEqualTo(dto.getDateClosed());
        assertThat(courtCase.getClient().getId()).isEqualTo(dto.getClient().getId());
        assertThat(courtCase.getJudge().getId()).isEqualTo(dto.getJudge().getId());
        assertThat(courtCase.getAttorney().getId()).isEqualTo(dto.getAttorney().getId());
        assertThat(courtCase.getChargedCounts().size())
            .isEqualTo(dto.getChargedCounts().size());
        assertThat(courtCase.getChargedCounts().get(1).getId())
            .isEqualTo(dto.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToMinimalDto() {
        
        CaseMinimalDto minimalDto = caseMapper.toCaseMinimalDto(courtCase);
        
        assertThat(courtCase.getId()).isEqualTo(minimalDto.getId());
        assertThat(courtCase.getCaseNumber()).isEqualTo(minimalDto.getCaseNumber());
        assertThat(courtCase.getCaseStatus()).isEqualTo(minimalDto.getCaseStatus());
        assertThat(courtCase.getDateOpened()).isEqualTo(minimalDto.getDateOpened());
        assertThat(courtCase.getDateClosed()).isEqualTo(minimalDto.getDateClosed());
        assertThat(courtCase.getChargedCounts().size())
            .isEqualTo(minimalDto.getChargedCounts().size());
        assertThat(courtCase.getChargedCounts().get(1).getId())
            .isEqualTo(minimalDto.getChargedCounts().get(1).getId());
    }
}