package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.CaseStatus;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Client;
import com.pdclientmanager.model.entity.Judge;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class CaseServiceImplTest {
    
    @Mock
    private CaseRepository repository;
    
    @Mock
    private CaseMapper mapper;
    
    private CaseService caseService;
    
    private CaseDto caseDto;
    private CaseFormDto caseFormDto;
    private Case courtCase;
    private List<CaseDto> caseDtoList;
    private List<Case> caseList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        caseService = new CaseServiceImpl(repository, mapper);
        
        caseDto = new CaseDto.CaseDtoBuilder()
                .withId(1L)
                .withCaseNumber("00J0001")
                .withCaseStatus(CaseStatus.OPEN)
                .withDateOpened(LocalDate.of(2000, 01, 01))
                .withDateClosed(null)
                .withClient(new ClientMinimalDto.ClientMinimalDtoBuilder()
                        .build())
                .withJudge(new JudgeDto.JudgeDtoBuilder()
                        .build())
                .withAttorney(new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
                        .build())
                .build();
        caseDto.addChargedCount(new ChargedCountDto.ChargedCountDtoBuilder()
                .withCountNumber(1)
                .withCharge(new ChargeDto.ChargeDtoBuilder()
                        .build())
                .build());
        
        caseFormDto = new CaseFormDto.CaseFormDtoBuilder()
                .withId(1L)
                .withCaseNumber("00J0001")
                .withDateOpened("01/01/2000")
                .withDateClosed("")
                .withClientId(1L)
                .withJudgeId(1L)
                .withAttorneyId(1L)
                .build();
        caseFormDto.addChargedCount(1, 1L);
        
        courtCase = new Case.CaseBuilder()
                .withId(1L)
                .withCaseNumber("00J0001")
                .withCaseStatus(CaseStatus.OPEN)
                .withDateOpened(LocalDate.of(2000, 01, 01))
                .withDateClosed(null)
                .withClient(new Client.ClientBuilder()
                        .build())
                .withJudge(new Judge.JudgeBuilder()
                        .build())
                .withAttorney(new Attorney.AttorneyBuilder()
                        .build())
                .build();
        courtCase.addChargedCount(new ChargedCount.ChargedCountBuilder()
                .withCountNumber(1)
                .withCharge(new Charge.ChargeBuilder()
                        .build())
                .build());
        
        caseDtoList = new ArrayList<>();
        caseDtoList.add(caseDto);
        
        caseList = new ArrayList<>();
        caseList.add(courtCase);
    }
    
    @Test
    public void save_WithValidFormDto_CallsRepositorySaveMethod() {
        
        when(mapper.toCaseFromCaseFormDto(
                eq(caseFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(courtCase);
        
        Long returnedId = caseService.save(caseFormDto);
        
        assertThat(returnedId).isEqualTo(courtCase.getId());
        verify(mapper).toCaseFromCaseFormDto(eq(caseFormDto),
                any(CycleAvoidingMappingContext.class));
        verify(repository).save(courtCase);
        
    }
    
    @Test
    public void findById_WithValidId_ReturnsCaseDto() {
        
        when(mapper.toCaseDto(courtCase)).thenReturn(caseDto);
        when(repository.findById(1L)).thenReturn(Optional.of(courtCase));
        
        CaseDto dtoFromService = caseService.findById(1L);
        
        assertThat(dtoFromService.getId()).isEqualTo(courtCase.getId());
        assertThat(dtoFromService.getClient().getId()).isEqualTo(courtCase.getClient().getId());
        assertThat(dtoFromService.getAttorney().getId()).isEqualTo(courtCase.getAttorney().getId());
        assertThat(dtoFromService.getChargedCounts().get(1).getId())
            .isEqualTo(courtCase.getChargedCounts().get(1).getId());
        verify(mapper).toCaseDto(courtCase);
        verify(repository).findById(1L);
    }
    
    @Test
    public void findAll_ReturnsDtoList() {
        
        when(mapper.toCaseDtoList(caseList)).thenReturn(caseDtoList);
        when(repository.findAll()).thenReturn(caseList);
        
        List<CaseDto> listFromService = caseService.findAll();
        
        assertThat(listFromService.size()).isEqualTo(caseList.size());
        assertThat(listFromService.get(0).getId()).isEqualTo(caseList.get(0).getId());
        assertThat(listFromService.get(0).getClient().getId())
            .isEqualTo(caseList.get(0).getClient().getId());
        assertThat(listFromService.get(0).getAttorney().getId())
            .isEqualTo(caseList.get(0).getAttorney().getId());
        assertThat(listFromService.get(0).getChargedCounts().get(1).getId())
            .isEqualTo(caseList.get(0).getChargedCounts().get(1).getId());
        verify(mapper).toCaseDtoList(caseList);
        verify(repository).findAll();
    }
    
    @Test
    public void delete_WithValidEntity_CallsRepositoryDeleteMethod() {
        
        when(mapper.toCase(eq(caseDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(courtCase);
        
        caseService.delete(caseDto);
        
        verify(mapper).toCase(eq(caseDto), any(CycleAvoidingMappingContext.class));
        verify(repository).delete(courtCase);
    }
    
    @Test
    public void deleteById_WithValidId_CallsRepositoryDeleteByIdMethod() {
        
        when(repository.findById(1L)).thenReturn(Optional.of(courtCase));
        
        caseService.deleteById(1L);
        
        verify(repository).deleteById(1L);
    }
}
