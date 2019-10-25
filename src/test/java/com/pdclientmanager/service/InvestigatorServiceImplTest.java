package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class InvestigatorServiceImplTest {
    
    @Mock
    private InvestigatorRepository repository;
    
    @Mock
    private InvestigatorMapper mapper;
    
    private InvestigatorService investigatorService;
    
    private InvestigatorDto investigatorDto;
    private InvestigatorFormDto investigatorFormDto;
    private Investigator investigator;
    private List<InvestigatorDto> investigatorDtoList;
    private List<Investigator> investigatorList;

    @BeforeEach
    public void setUp() {
        
        initMocks(this);
        investigatorService = new InvestigatorServiceImpl(repository, mapper);
    
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
    public void save_WithValidinvestigator_CallsRepositorySaveMethod() {
        
        when(mapper.toInvestigatorFromInvestigatorFormDto(
                eq(investigatorFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        assertThat(investigatorService.save(investigatorFormDto)).isEqualTo(investigator.getId());
        verify(repository).save(investigator);
    }
    
    @Test
    public void findById_WithValidId_ReturnsInvestigatorDto() {
        
        when(repository.findById(1L)).thenReturn(Optional.of(investigator));
        when(mapper.toInvestigatorDto(
                eq(investigator)))
            .thenReturn(investigatorDto);
        
        InvestigatorDto dtoFromService = investigatorService.findById(1L);
        
        assertThat(dtoFromService).isEqualTo(investigatorDto);
        verify(repository).findById(1L);
    }
    
    @Test
    public void findAll_ReturnsDtoList() {
        
        when(repository.findAll()).thenReturn(investigatorList);
        when(mapper.toInvestigatorDtoList(
                eq(investigatorList)))
            .thenReturn(investigatorDtoList);
        
        List<InvestigatorDto> listFromService = investigatorService.findAll();
        
        assertThat(listFromService).isEqualTo(investigatorDtoList);
        verify(repository).findAll();
    }
    
    @Test
    public void delete_WithValidEntity_CallsRepositoryDeleteMethod() {
        
        when(mapper.toInvestigator(
                eq(investigatorDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        investigatorService.delete(investigatorDto);
        
        verify(repository).delete(investigator);
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        investigatorService.deleteById(1L);
        
        verify(repository).deleteById(1L);
    }
}
