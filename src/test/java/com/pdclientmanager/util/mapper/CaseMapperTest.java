package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.service.JudgeService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class CaseMapperTest {
    
    @Mock
    private ClientService clientService;
    
    @Mock
    private JudgeService judgeService;
    
    @Mock
    private AttorneyService attorneyService;
    
    @Mock
    private ChargeService chargeService;
    
    private CaseForm caseFormDto;
    private Case courtCase;
    
    @Autowired
    @InjectMocks
    CaseMapper caseMapper;
    
    @BeforeEach
    public void setUp() {
        
        initMocks(this);
        
        caseFormDto = new CaseForm.CaseFormDtoBuilder()
                .withAttorneyId(1L)
                .withCaseNumber("20J0001")
                .withClientId(2L)
                .withJudgeId(3L)
                .build();
        
        courtCase = new Case.CaseBuilder()
            .withAttorney(new Attorney.AttorneyBuilder().build())
            .withClient(new Client.ClientBuilder().build())
            .withJudge(new Judge.JudgeBuilder().build())
            .build();
        
        ChargedCount testCount = new ChargedCount.ChargedCountBuilder()
            .withCharge(new Charge.ChargeBuilder().build())
            .build();
        caseFormDto.addChargedCount(testCount);
        courtCase.addChargedCount(testCount);
    }
    
    // Mapping between CaseDto and Case entity
    
    @Test
    public void mapper_ShouldMapFormToEntity() {
        
        when(chargeService.findById(caseFormDto.getChargedCountsIds().get(1), Charge.class))
            .thenReturn(new Charge.ChargeBuilder().build());
        
        when(clientService.findById(caseFormDto.getClientId(), Client.class))
            .thenReturn(new Client.ClientBuilder()
                    .withId(caseFormDto.getClientId()).build());
        
        when(judgeService.findById(caseFormDto.getJudgeId(), Judge.class))
            .thenReturn(new Judge.JudgeBuilder()
                    .withId(caseFormDto.getJudgeId()).build());
        
        when(attorneyService.findById(caseFormDto.getAttorneyId(), Attorney.class))
            .thenReturn(new Attorney.AttorneyBuilder()
                    .withId(caseFormDto.getAttorneyId()).build());
        
        Case entity = caseMapper.toCaseFromCaseFormDto(caseFormDto, new CycleAvoidingMappingContext());
        
        assertThat(entity.getId()).isEqualTo(caseFormDto.getId());
        assertThat(entity.getCaseNumber()).isEqualTo(caseFormDto.getCaseNumber());
        assertThat(entity.getDateOpened()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(entity.getDateClosed()).isNull();
        assertThat(entity.getClient().getId()).isEqualTo(caseFormDto.getClientId());
        assertThat(entity.getJudge().getId()).isEqualTo(caseFormDto.getJudgeId());
        assertThat(entity.getAttorney().getId()).isEqualTo(caseFormDto.getAttorneyId());
        assertThat(entity.getChargedCounts().size())
            .isEqualTo(caseFormDto.getChargedCountsIds().size());
        assertThat(entity.getChargedCounts().get(1).getCharge().getId())
            .isEqualTo(caseFormDto.getChargedCountsIds().get(1));
    }
    
    @Test
    public void mapper_ShouldMapEntityToForm() {
                
        CaseForm formDto = caseMapper.toCaseFormDtoFromCase(courtCase);
                
        assertThat(formDto.getId()).isEqualTo(courtCase.getId());
        assertThat(formDto.getCaseNumber()).isEqualTo(courtCase.getCaseNumber());
        assertThat(formDto.getDateOpened()).isEqualTo("1/1/00");
        assertThat(formDto.getDateClosed()).isEqualTo("");
        assertThat(formDto.getClientId()).isEqualTo(courtCase.getClient().getId());
        assertThat(formDto.getJudgeId()).isEqualTo(courtCase.getJudge().getId());
        assertThat(formDto.getAttorneyId()).isEqualTo(courtCase.getAttorney().getId());
        assertThat(formDto.getChargedCountsIds().size())
            .isEqualTo(courtCase.getChargedCounts().size());
        assertThat(formDto.getChargedCountsIds().get(1))
            .isEqualTo(courtCase.getChargedCounts().get(1).getCharge().getId());
    }
}