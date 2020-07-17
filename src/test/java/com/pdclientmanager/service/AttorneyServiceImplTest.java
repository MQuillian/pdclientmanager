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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Investigator;
import com.pdclientmanager.repository.entity.WorkingStatus;
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
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private AttorneyProjection attorneyProj;
    private AttorneyForm attorneyFormDto;
    private Attorney attorney;
    private List<AttorneyProjection> attorneyProjList;
    private List<Attorney> attorneyList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        attorneyService = new AttorneyServiceImpl(repository, mapper);
        
        // Creating data for tests
        
        attorneyProj = factory.createProjection(AttorneyProjection.class);
        attorneyProj.setId(1L);
        attorneyProj.setName("Test Attorney");
        attorneyProj.setWorkingStatus(WorkingStatus.ACTIVE);
        
        attorneyFormDto = new AttorneyForm.AttorneyFormDtoBuilder()
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
        
        attorneyProjList = new ArrayList<>();
        attorneyProjList.add(attorneyProj);
        
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
    public void findById_WithValidId_ReturnsAttorneyProjection() {
 
        when(repository.findById(1L, AttorneyProjection.class))
            .thenReturn(Optional.of(attorneyProj));
        
        AttorneyProjection projFromService = attorneyService.findById(1L, AttorneyProjection.class);
        
        assertThat(projFromService).isEqualTo(attorneyProj);
        verify(repository).findById(1L, AttorneyProjection.class);
    }
    
    @Test
    public void findAll_ReturnsDtoList() {
        
        when(repository.findAllProjectedBy()).thenReturn(attorneyProjList);
        
        List<AttorneyProjection> listFromService = attorneyService.findAll();
        
        assertThat(listFromService).isEqualTo(attorneyProjList);
        verify(repository).findAllProjectedBy();
    }
    
    @Test
    public void delete_WithEmptyOpenCaseload_CallsRepositoryDeleteMethod() {
        
        when(repository.findById(attorneyProj.getId(), Attorney.class))
            .thenReturn(Optional.of(attorney));
        
        attorney.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.delete(attorneyProj)).isEqualTo(true);

        verify(repository).delete(attorney);
    }
    
    @Test
    public void delete_WithOpenCaseload_DoesNotCallRepositoryDeleteMethod() {
        
        when(repository.findById(attorneyProj.getId(), Attorney.class))
            .thenReturn(Optional.of(attorney));
        attorney.getCaseload().add(new Case.CaseBuilder()
                .build());
        
        assertThat(attorneyService.delete(attorneyProj)).isEqualTo(false);
        verify(repository, never()).delete(attorney);
    }
    
    @Test
    public void deleteById_WithValidIdAndEmptyOpenCaseload_CallsRepositoryDeleteByIdMethod() {
        when(repository.findById(1L, Attorney.class)).thenReturn(Optional.of(attorney));
        attorney.setCaseload(new ArrayList<>());
        
        assertThat(attorneyService.deleteById(1L)).isEqualTo(true);
        verify(repository).delete(attorney);
    }
    
    @Test
    public void deleteById_WithValidIdAndOpenCaseload_DoesNotCallRepositoryDeleteByIdMethod() {
        when(repository.findById(1L,  Attorney.class)).thenReturn(Optional.of(attorney));
        attorney.getCaseload().add(new Case.CaseBuilder().build());
        
        assertThat(attorneyService.deleteById(1L)).isEqualTo(false);
        verify(repository, never()).delete(attorney);
    }
}
