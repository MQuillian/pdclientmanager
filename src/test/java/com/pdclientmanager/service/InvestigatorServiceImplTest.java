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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.model.projection.InvestigatorProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Investigator;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
class InvestigatorServiceImplTest {
    
    @Mock
    private InvestigatorRepository investigatorRepository;
    
    @Mock
    private AttorneyRepository attorneyRepository;
    
    @Mock
    private InvestigatorMapper mapper;
    
    private InvestigatorService investigatorService;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private InvestigatorProjection investigatorProj;
    private InvestigatorForm investigatorFormDto;
    private Investigator investigator;
    private List<InvestigatorProjection> investigatorProjList;
    private List<Investigator> investigatorList;

    @BeforeEach
    public void setUp() {
        
        initMocks(this);
        investigatorService = new InvestigatorServiceImpl(investigatorRepository,
                attorneyRepository, mapper);
    
        investigatorProj = factory.createProjection(InvestigatorProjection.class);
        investigatorProj.setId(1L);
        investigatorProj.setName("Active Investigator");
        investigatorProj.setWorkingStatus(WorkingStatus.ACTIVE);
        
        investigatorFormDto = new InvestigatorForm.InvestigatorFormDtoBuilder()
                .withId(1L)
                .withName("Test InvestigatorFormDto")
                .build();
        
        investigator = new Investigator.InvestigatorBuilder()
                .withId(1L)
                .withName("Test Investigator")
                .build();
        investigator.getAssignedAttorneys().add(new Attorney.AttorneyBuilder()
                .build());
        
        investigatorProjList = new ArrayList<>();
        investigatorProjList.add(investigatorProj);
        
        investigatorList = new ArrayList<>();
        investigatorList.add(investigator);
    }
    
    @Test
    public void save_WithValidinvestigator_CallsRepositorySaveMethod() {
        
        when(mapper.toInvestigatorFromInvestigatorForm(
            eq(investigatorFormDto), any(CycleAvoidingMappingContext.class)))
            .thenReturn(investigator);
        
        assertThat(investigatorService.save(investigatorFormDto)).isEqualTo(investigator.getId());
        verify(investigatorRepository).save(investigator);
    }
    
    @Test
    public void findById_WithValidId_ReturnsInvestigatorDto() {
        
        when(investigatorRepository.findById(1L, InvestigatorProjection.class))
            .thenReturn(Optional.of(investigatorProj));
        
        
        assertThat(investigatorService.findById(1L, InvestigatorProjection.class))
            .isEqualTo(investigatorProj);
        verify(investigatorRepository).findById(1L, InvestigatorProjection.class);
    }
    
    @Test
    public void findAll_ReturnsDtoList() {
        
        when(investigatorRepository.findAllBy(InvestigatorProjection.class)).thenReturn(investigatorProjList);
        
        assertThat(investigatorService.findAll()).isEqualTo(investigatorProjList);
        verify(investigatorRepository).findAllBy(InvestigatorProjection.class);
    }
    
    @Test
    public void delete_WithValidEntity_CallsRepositoryDeleteMethod() {
        
        
        investigatorService.delete(investigatorProj);
        
        verify(investigatorRepository).deleteById(investigatorProj.getId());
    }
    
    @Test
    public void deleteById_WithValidId_CallsDaoDeleteMethod() {
        
        investigatorService.deleteById(1L);
        
        verify(investigatorRepository).deleteById(1L);
    }
}
