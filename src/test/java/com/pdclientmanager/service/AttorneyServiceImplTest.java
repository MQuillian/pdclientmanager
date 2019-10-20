package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class AttorneyServiceImplTest {
    
    @Mock
    private GenericEmployeeDaoImpl<Attorney> attorneyDao;
    
    @Mock
    AttorneyMapper attorneyMapper;
    
    private AttorneyService attorneyService;
    
    private AttorneyDto attorneyDto;
    private AttorneyFormDto attorneyFormDto;
    private Attorney attorney;
    private List<AttorneyDto> attorneyDtoList;
    private List<Attorney> attorneyList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        attorneyService = new AttorneyServiceImpl(attorneyDao, attorneyMapper);
        
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
    public void persist_WithValidFormDto_CallsDaoPersistMethod() {
        
        when(attorneyMapper.toAttorneyFromAttorneyFormDto(
                eq(attorneyFormDto), any(CycleAvoidingMappingContext.class)))    
            .thenReturn(attorney);
        
        assertThat(attorneyService.persist(attorneyFormDto)).isEqualTo(attorney.getId());
        verify(attorneyDao).persist(attorney);
    }
    
    @Test
    public void getById_WithValidId_ReturnsAttorney() {
 
        when(attorneyDao.getById(1L)).thenReturn(attorney);
        when(attorneyMapper.toAttorneyDto(
                eq(attorney)))
            .thenReturn(attorneyDto);
        
        AttorneyDto dtoFromService = attorneyService.getById(1L);
        
        assertThat(dtoFromService).isEqualTo(attorneyDto);
        verify(attorneyDao).getById(1L);
    }
    
    @Test
    public void getAll_ReturnsList() {
        
        when(attorneyDao.getAll()).thenReturn(attorneyList);
        when(attorneyMapper.toAttorneyDtoList(
                eq(attorneyList)))
            .thenReturn(attorneyDtoList);
        
        List<AttorneyDto> listFromService = attorneyService.getAll();
        
        assertThat(listFromService).isEqualTo(attorneyDtoList);
        verify(attorneyDao).getAll();
    }
    
    @Test
    public void merge_WithValidFormDto_CallsDaoMergeMethod() {
        
        when(attorneyMapper.toAttorneyFromAttorneyFormDto(
                eq(attorneyFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(attorney);
        
        assertThat(attorneyService.merge(attorneyFormDto)).isEqualTo(attorney.getId());
        verify(attorneyDao).merge(attorney);
    }
    
    @Test
    public void delete_WithEmptyOpenCaseload_CallsDaoDeleteMethod() {
        
        when(attorneyMapper.toAttorney(
                eq(attorneyDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(attorney);
        
        attorneyDto.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.delete(attorneyDto)).isEqualTo(true);
        verify(attorneyDao).delete(attorney);
    }
    
    @Test
    public void delete_WithOpenCaseload_DoesNotCallDaoDeleteMethod() {
        
        attorneyDto.getCaseload().add(new CaseMinimalDto.CaseMinimalDtoBuilder()
                .build());
        
        assertThat(attorneyService.delete(attorneyDto)).isEqualTo(false);
        verifyZeroInteractions(attorneyDao);
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        when(attorneyDao.getById(1L)).thenReturn(attorney);
        
        attorneyService.deleteById(1L);
        
        verify(attorneyDao).delete(attorney);
    }
}
