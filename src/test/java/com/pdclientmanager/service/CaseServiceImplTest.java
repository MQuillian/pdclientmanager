package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.dao.CaseDaoImpl;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseDto;
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
import com.pdclientmanager.util.mapper.CaseMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class CaseServiceImplTest {
    
    @Mock
    private CaseDaoImpl caseDao;
    
    @Autowired
    CaseMapper caseMapper;
    
    private CaseService caseService;
    
    CaseDto caseDto;
    Case courtCase;
    List<CaseDto> caseDtoList;
    List<Case> caseList;
    ArgumentCaptor<Case> argument = ArgumentCaptor.forClass(Case.class);
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        caseService = new CaseServiceImpl(caseDao, caseMapper);
        
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
    
    //persist, getById, getAll, merge, delete, deleteById
    @Test
    public void persist_WithValidCaseDto_CallsDaoPersistMethod() {
        
        caseService.persist(caseDto);
        verify(caseDao).persist(argument.capture());
        
        Case persistedCase = argument.getValue();
        
        assertThat(persistedCase.getId()).isEqualTo(caseDto.getId());
        assertThat(persistedCase.getClient().getId()).isEqualTo(caseDto.getClient().getId());
        assertThat(persistedCase.getAttorney().getId()).isEqualTo(caseDto.getAttorney().getId());
        assertThat(persistedCase.getChargedCounts().get(1).getId())
            .isEqualTo(caseDto.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void getById_WithValidId_ReturnsCaseDto() {
        
        when(caseDao.getById(1L)).thenReturn(courtCase);
        
        CaseDto dtoFromService = caseService.getById(1L);
        
        assertThat(dtoFromService.getId()).isEqualTo(courtCase.getId());
        assertThat(dtoFromService.getClient().getId()).isEqualTo(courtCase.getClient().getId());
        assertThat(dtoFromService.getAttorney().getId()).isEqualTo(courtCase.getAttorney().getId());
        assertThat(dtoFromService.getChargedCounts().get(1).getId())
            .isEqualTo(courtCase.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void getAll_ReturnsDtoList() {
        
        when(caseDao.getAll()).thenReturn(caseList);
        
        List<CaseDto> listFromService = caseService.getAll();
        verify(caseDao).getAll();
        
        assertThat(listFromService.size()).isEqualTo(caseList.size());
        assertThat(listFromService.get(0).getId()).isEqualTo(caseList.get(0).getId());
        assertThat(listFromService.get(0).getClient().getId())
            .isEqualTo(caseList.get(0).getClient().getId());
        assertThat(listFromService.get(0).getAttorney().getId())
            .isEqualTo(caseList.get(0).getAttorney().getId());
        assertThat(listFromService.get(0).getChargedCounts().get(1).getId())
            .isEqualTo(caseList.get(0).getChargedCounts().get(1).getId());
    }
    
    @Test
    public void merge_WithValidEntity_CallsDaoMergeMethod() {
        
        caseService.merge(caseDto);
        verify(caseDao).merge(argument.capture());
        
        Case mergedCase = argument.getValue();
        
        assertThat(mergedCase.getId()).isEqualTo(caseDto.getId());
        assertThat(mergedCase.getClient().getId()).isEqualTo(caseDto.getClient().getId());
        assertThat(mergedCase.getAttorney().getId()).isEqualTo(caseDto.getAttorney().getId());
        assertThat(mergedCase.getChargedCounts().get(1).getId())
            .isEqualTo(caseDto.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void delete_WithValidEntity_CallsDaoDeleteMethod() {
        
        caseService.delete(caseDto);
        verify(caseDao).delete(argument.capture());
        
        Case deletedCase = argument.getValue();
        
        assertThat(deletedCase.getId()).isEqualTo(caseDto.getId());
        assertThat(deletedCase.getClient().getId()).isEqualTo(caseDto.getClient().getId());
        assertThat(deletedCase.getAttorney().getId()).isEqualTo(caseDto.getAttorney().getId());
        assertThat(deletedCase.getChargedCounts().get(1).getId())
            .isEqualTo(caseDto.getChargedCounts().get(1).getId());
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        when(caseDao.getById(1L)).thenReturn(courtCase);
        
        caseService.deleteById(1L);
        verify(caseDao).delete(courtCase);
    }
}
