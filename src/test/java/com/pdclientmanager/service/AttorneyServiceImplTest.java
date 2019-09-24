package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.util.mapper.AttorneyMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class AttorneyServiceImplTest {
    
    @Mock
    private GenericEmployeeDaoImpl<Attorney> attorneyDao;
    
    @Autowired
    AttorneyMapper attorneyMapper;
    
    private EmployeeService<AttorneyDto, Attorney> attorneyService;
    
    AttorneyDto attorneyDto;
    Attorney attorney;
    List<AttorneyDto> attorneyDtoList;
    List<Attorney> attorneyList;
    ArgumentCaptor<Attorney> argument = ArgumentCaptor.forClass(Attorney.class);
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        attorneyService = new AttorneyServiceImpl(attorneyDao, attorneyMapper);
        
        attorneyDto = new AttorneyDto.AttorneyDtoBuilder()
                .withId(1L)
                .withName("Test AttorneyDto")
                .withInvestigator(new InvestigatorMinimalDto.InvestigatorMinimalDtoBuilder()
                        .build())
                .build();
        
        attorney = new Attorney.AttorneyBuilder()
                .withId(1L)
                .withName("Test Attorney")
                .withInvestigator(new Investigator.InvestigatorBuilder()
                        .build())
                .build();
        
        attorneyDtoList = new ArrayList<>();
        attorneyDtoList.add(attorneyDto);
        
        attorneyList = new ArrayList<>();
        attorneyList.add(attorney);
    }
    
    @Test
    public void persist_WithValidAttorneyDto_CallsDaoPersistMethod() {
        
        attorneyService.persist(attorneyDto);
        verify(attorneyDao).persist(argument.capture());
        
        Attorney persistedAttorney = argument.getValue();
        
        assertThat(persistedAttorney.getId()).isEqualTo(attorneyDto.getId());
        assertThat(persistedAttorney.getInvestigator().getId())
            .isEqualTo(attorneyDto.getInvestigator().getId());
    }
    
    @Test
    public void getById_WithValidId_ReturnsAttorney() {
 
        when(attorneyDao.getById(1L)).thenReturn(attorney);
        
        AttorneyDto dtoFromService = attorneyService.getById(1L);
        verify(attorneyDao).getById(1L);
        
        assertThat(dtoFromService.getId()).isEqualTo(attorney.getId());
        assertThat(dtoFromService.getInvestigator().getId())
            .isEqualTo(attorneyDto.getInvestigator().getId());
    }
    
    @Test
    public void getAll_ReturnsList() {
        
        when(attorneyDao.getAll()).thenReturn(attorneyList);
        
        List<AttorneyDto> listFromService = attorneyService.getAll();
        
        assertThat(listFromService.size())
            .isEqualTo(attorneyList.size());
        assertThat(listFromService.get(0).getId())
            .isEqualTo(attorneyList.get(0).getId());
        assertThat(listFromService.get(0).getInvestigator().getId())
            .isEqualTo(attorneyList.get(0).getInvestigator().getId());
        
        verify(attorneyDao).getAll();
    }
    
    @Test
    public void merge_WithValidEntity_CallsDaoMergeMethod() {
        
        attorneyService.merge(attorneyDto);
        verify(attorneyDao).merge(argument.capture());
        
        Attorney mergedAttorney = argument.getValue();
        
        assertThat(mergedAttorney.getId()).isEqualTo(attorneyDto.getId());
        assertThat(mergedAttorney.getInvestigator().getId()).isEqualTo(attorneyDto.getInvestigator().getId());
    }
    
    @Test
    public void delete_WithEmptyOpenCaseload_CallsDaoDeleteMethod() {
        
        attorneyDto.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.delete(attorneyDto)).isEqualTo(true);
        verify(attorneyDao).delete(argument.capture());
        
        Attorney deletedAttorney = argument.getValue();
        
        assertThat(deletedAttorney.getId()).isEqualTo(attorneyDto.getId());
        assertThat(deletedAttorney.getInvestigator().getId()).isEqualTo(attorneyDto.getInvestigator().getId());
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
