package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class AttorneyServiceImplTest {
    
    @Mock
    private AttorneyRepository repository;
    
    @Mock
    private AttorneyMapper mapper;
    
    private AttorneyService attorneyService;
    
    private AttorneyDto attorneyDto;
    private AttorneyFormDto attorneyFormDto;
    private Attorney attorney;
    private List<AttorneyDto> attorneyDtoList;
    private List<Attorney> attorneyList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        attorneyService = new AttorneyServiceImpl(repository, mapper);
        
        // Creating data for tests
        
        attorneyDto = new AttorneyDto.AttorneyDtoBuilder()
                .withId(1L)
                .withName("Test Attorney")
                .withInvestigator(new InvestigatorMinimalDto.InvestigatorMinimalDtoBuilder()
                        .withId(1L)
                        .build())
                .build();
        
        attorneyFormDto = new AttorneyFormDto.AttorneyFormDtoBuilder()
                .withId(1L)
                .withName("Test Attorney")
                .withInvestigatorId(1L)
                .build();
        
        attorney = new Attorney.AttorneyBuilder()
                .withId(1L)
                .withName("Test Attorney")
                .withInvestigator(new Investigator.InvestigatorBuilder()
                        .withId(1L)
                        .build())
                .build();
        
        attorneyDtoList = new ArrayList<>();
        attorneyDtoList.add(attorneyDto);
        
        attorneyList = new ArrayList<>();
        attorneyList.add(attorney);
    }
    
    @Test
    public void save_WithValidFormDto_CallsRepositorySaveMethod() {
        
        when(mapper.toAttorneyFromAttorneyFormDto(
                eq(attorneyFormDto), any(CycleAvoidingMappingContext.class)))    
            .thenReturn(attorney);
        
        Long returnedId = attorneyService.save(attorneyFormDto);
        
        assertThat(returnedId).isEqualTo(attorney.getId());
        verify(mapper).toAttorneyFromAttorneyFormDto(eq(attorneyFormDto),
                any(CycleAvoidingMappingContext.class));
        verify(repository).save(attorney);
    }
    
    @Test
    public void findById_WithValidId_ReturnsAttorneyDto() {
 
        when(mapper.toAttorneyDto(attorney))
            .thenReturn(attorneyDto);
        when(repository.findById(1L)).thenReturn(Optional.of(attorney));
        
        AttorneyDto dtoFromService = attorneyService.findById(1L);
        
        assertThat(dtoFromService).isEqualTo(attorneyDto);
        verify(mapper).toAttorneyDto(attorney);
        verify(repository).findById(1L);
    }
    
    @Test
    public void findAll_ReturnsDtoList() {
        
        when(mapper.toAttorneyDtoList(attorneyList))
            .thenReturn(attorneyDtoList);
        when(repository.findAll()).thenReturn(attorneyList);
        
        List<AttorneyDto> listFromService = attorneyService.findAll();
        
        assertThat(listFromService).isEqualTo(attorneyDtoList);
        verify(mapper).toAttorneyDtoList(attorneyList);
        verify(repository).findAll();
    }
    
    @Test
    public void delete_WithEmptyOpenCaseload_CallsRepositoryDeleteMethod() {
        when(mapper.toAttorney(
                eq(attorneyDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(attorney);
        attorney.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.delete(attorneyDto)).isEqualTo(true);
        verify(mapper).toAttorney(eq(attorneyDto),
                any(CycleAvoidingMappingContext.class));
        verify(repository).delete(attorney);
    }
    
    @Test
    public void delete_WithOpenCaseload_DoesNotCallRepositoryDeleteMethod() {
        when(mapper.toAttorney(
                eq(attorneyDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(attorney);
        attorney.getCaseload().add(new Case.CaseBuilder()
                .build());
        attorneyDto.getCaseload().add(new CaseMinimalDto.CaseMinimalDtoBuilder().build());
        
        assertThat(attorneyService.delete(attorneyDto)).isEqualTo(false);
        verify(repository, never()).delete(attorney);
    }
    
    @Test
    public void deleteById_WithValidIdAndEmptyOpenCaseload_CallsRepositoryDeleteByIdMethod() {
        when(repository.findById(1L)).thenReturn(Optional.of(attorney));
        attorney.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.deleteById(1L)).isEqualTo(true);
        verify(repository).delete(attorney);
    }
    
    @Test
    public void deleteById_WithValidIdAndOpenCaseload_DoesNotCallRepositoryDeleteByIdMethod() {
        when(repository.findById(1L)).thenReturn(Optional.of(attorney));
        attorney.getCaseload().add(new Case.CaseBuilder().build());
        
        assertThat(attorneyService.deleteById(1L)).isEqualTo(false);
        verify(repository, never()).delete(attorney);
    }
}
