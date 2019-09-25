package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class InvestigatorServiceImplTest {
    
    @Mock
    private GenericEmployeeDaoImpl<Investigator> investigatorDao;
    
    @Autowired
    InvestigatorMapper investigatorMapper;
    
    private EmployeeService<InvestigatorDto, Investigator> investigatorService;
    
    private InvestigatorDto investigatorDto;
    private Investigator investigator;
    private List<InvestigatorDto> investigatorDtoList;
    private List<Investigator> investigatorList;
    ArgumentCaptor<Investigator> argument = ArgumentCaptor.forClass(Investigator.class);

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
        
        investigatorService.persist(investigatorDto);
        verify(investigatorDao).persist(argument.capture());
        
        Investigator persistedInvestigator = argument.getValue();
        
        assertThat(persistedInvestigator.getId()).isEqualTo(investigatorDto.getId());
        assertThat(persistedInvestigator.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorDto.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void getById_WithValidId_ReturnsInvestigatorDto() {
        
        when(investigatorDao.getById(1L)).thenReturn(investigator);
        
        InvestigatorDto dtoFromService = investigatorService.getById(1L);
        
        assertThat(dtoFromService.getId()).isEqualTo(investigator.getId());
        assertThat(dtoFromService.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigator.getAssignedAttorneys().get(0).getId());
        
        verify(investigatorDao).getById(1L);
    }
    
    @Test
    public void getAll_ReturnsDtoList() {
        
        when(investigatorDao.getAll()).thenReturn(investigatorList);
        
        List<InvestigatorDto> listFromService = investigatorService.getAll();
        verify(investigatorDao).getAll();
        
        assertThat(listFromService.size())
            .isEqualTo(investigatorList.size());
        assertThat(listFromService.get(0).getId())
            .isEqualTo(investigatorList.get(0).getId());
        assertThat(listFromService.get(0).getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorList.get(0).getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void merge_WithValidEntity_CallsDaoMergeMethod() {
        
        investigatorService.merge(investigatorDto);
        verify(investigatorDao).merge(argument.capture());
        
        Investigator mergedInvestigator = argument.getValue();
        
        assertThat(mergedInvestigator.getId()).isEqualTo(investigatorDto.getId());
        assertThat(mergedInvestigator.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorDto.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void delete_WithValidEntity_CallsDaoDeleteMethod() {
        
        investigatorService.delete(investigatorDto);
        verify(investigatorDao).delete(argument.capture());
        
        Investigator deletedInvestigator = argument.getValue();
        
        assertThat(deletedInvestigator.getId()).isEqualTo(investigatorDto.getId());
        assertThat(deletedInvestigator.getAssignedAttorneys().get(0).getId())
            .isEqualTo(investigatorDto.getAssignedAttorneys().get(0).getId());
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        when(investigatorDao.getById(1L)).thenReturn(investigator);
        
        investigatorService.deleteById(1L);
        verify(investigatorDao).delete(investigator);
    }
}
