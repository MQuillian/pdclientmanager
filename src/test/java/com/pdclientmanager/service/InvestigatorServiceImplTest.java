package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class InvestigatorServiceImplTest {
    
    @Mock
    private GenericEmployeeDaoImpl<Investigator> investigatorDao;
    
    @Mock
    InvestigatorMapper investigatorMapper;
    
    private InvestigatorService investigatorService;
    
    private InvestigatorDto investigatorDto;
    private InvestigatorFormDto investigatorFormDto;
    private Investigator investigator;
    private List<InvestigatorDto> investigatorDtoList;
    private List<Investigator> investigatorList;

    @BeforeEach
    public void setUp() {
        
        initMocks(this);
        investigatorService = new InvestigatorServiceImpl(investigatorDao, investigatorMapper);
    
        investigatorDto = new InvestigatorDto.InvestigatorDtoBuilder()
                .withId(1L)
                .withName("Test InvestigatorDto")
                .build();
        investigatorDto.getAssignedAttorneys().add(new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
                .build());
        
        investigatorFormDto = new InvestigatorFormDto.InvestigatorFormDtoBuilder()
                .withId(1L)
                .withName("Test InvestigatorFormDto")
                .build();
        
        investigator = new Investigator.InvestigatorBuilder()
                .withId(1L)
                .withName("Test Investigator")
                .build();
        investigator.getAssignedAttorneys().add(new Attorney.AttorneyBuilder()
                .build());
        
        investigatorDtoList = new ArrayList<>();
        investigatorDtoList.add(investigatorDto);
        
        investigatorList = new ArrayList<>();
        investigatorList.add(investigator);
    }
    
    @Test
    public void persist_WithValidinvestigator_CallsDaoPersistMethod() {
        
        when(investigatorMapper.toInvestigatorFromInvestigatorFormDto(
                eq(investigatorFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        assertThat(investigatorService.persist(investigatorFormDto)).isEqualTo(investigator.getId());
        verify(investigatorDao).persist(investigator);
    }
    
    @Test
    public void getById_WithValidId_ReturnsInvestigatorDto() {
        
        when(investigatorDao.getById(1L)).thenReturn(investigator);
        when(investigatorMapper.toInvestigatorDto(
                eq(investigator)))
            .thenReturn(investigatorDto);
        
        InvestigatorDto dtoFromService = investigatorService.getById(1L);
        
        assertThat(dtoFromService).isEqualTo(investigatorDto);
        verify(investigatorDao).getById(1L);
    }
    
    @Test
    public void getAll_ReturnsDtoList() {
        
        when(investigatorDao.getAll()).thenReturn(investigatorList);
        when(investigatorMapper.toInvestigatorDtoList(
                eq(investigatorList)))
            .thenReturn(investigatorDtoList);
        
        List<InvestigatorDto> listFromService = investigatorService.getAll();
        
        assertThat(listFromService).isEqualTo(investigatorDtoList);
        verify(investigatorDao).getAll();
    }
    
    @Test
    public void merge_WithValidEntity_CallsDaoMergeMethod() {
        
        when(investigatorMapper.toInvestigatorFromInvestigatorFormDto(
                eq(investigatorFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        assertThat(investigatorService.merge(investigatorFormDto)).isEqualTo(investigator.getId());
        verify(investigatorDao).merge(investigator);
    }
    
    @Test
    public void delete_WithValidEntity_CallsDaoDeleteMethod() {
        
        when(investigatorMapper.toInvestigator(
                eq(investigatorDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        investigatorService.delete(investigatorDto);
        
        verify(investigatorDao).delete(investigator);
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        when(investigatorDao.getById(1L)).thenReturn(investigator);
        
        investigatorService.deleteById(1L);
        
        verify(investigatorDao).delete(investigator);
    }
}
