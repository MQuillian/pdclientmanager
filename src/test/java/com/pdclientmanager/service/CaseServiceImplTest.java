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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.ChargedCountId;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class CaseServiceImplTest {
    
    @Mock
    private CaseRepository caseRepository;
    
    @Mock
    private AttorneyRepository attorneyRepository;
    
    @Mock
    private CaseMapper mapper;
    
    private CaseService caseService;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private CaseProjection caseProj;
    private CaseForm caseFormDto;
    private Case courtCase;
    private List<CaseProjection> caseProjList;
    private List<Case> caseList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        caseService = new CaseServiceImpl(caseRepository, attorneyRepository, mapper);
        
        caseProj = factory.createProjection(CaseProjection.class);
        caseProj.setId(1L);
        caseProj.setCaseNumber("20J0001");
        caseProj.setDateOpened(LocalDate.of(2020, 1, 5));
        
        caseFormDto = new CaseForm.CaseFormDtoBuilder()
                .withId(1L)
                .withCaseNumber("00J0001")
                .withDateOpened("01/01/2000")
                .withDateClosed("")
                .withClientId(1L)
                .withJudgeId(1L)
                .withAttorneyId(1L)
                .build();
        caseFormDto.addChargedCount(1, 1L, "Default");
        
        courtCase = new Case.CaseBuilder()
                .withId(1L)
                .withCaseNumber("00J0001")
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
                .withId(new ChargedCountId(1, courtCase))
                .withCharge(new Charge.ChargeBuilder()
                        .build())
                .build());
        
        caseProjList = new ArrayList<>();
        caseProjList.add(caseProj);
        
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
        verify(caseRepository).save(courtCase);
        
    }
    
    @Test
    public void findById_WithValidId_ReturnsCaseProjection() {
        
        when(caseRepository.findById(1L, CaseProjection.class))
            .thenReturn(Optional.of(caseProj));
        
        assertThat(caseService.findById(1L, CaseProjection.class)).isEqualTo(caseProj);
        verify(caseRepository).findById(1L, CaseProjection.class);
    }
    
    @Test
    public void findAll_ReturnsProjectionList() {
        
        when(caseRepository.findAllBy(CaseProjection.class)).thenReturn(caseProjList);
        
        assertThat(caseService.findAll(CaseProjection.class)).isEqualTo(caseProjList);

        verify(caseRepository).findAllBy(CaseProjection.class);
    }
    
    @Test
    public void delete_WithValidEntity_CallsRepositoryDeleteByIdMethod() {
        
        caseService.delete(caseProj);
        
        verify(caseRepository).deleteById(caseProj.getId());
    }
    
    @Test
    public void deleteById_WithValidId_CallsRepositoryDeleteByIdMethod() {
        
        caseService.deleteById(1L);
        
        verify(caseRepository).deleteById(1L);
    }
}
