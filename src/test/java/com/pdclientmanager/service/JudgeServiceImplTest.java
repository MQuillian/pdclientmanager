package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.JudgeRepository;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.util.mapper.JudgeMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class JudgeServiceImplTest {
    
    @Mock
    private JudgeRepository judgeRepositoryMock;
    
    @Mock CaseRepository caseRepositoryMock;
    
    @Mock
    private JudgeMapper judgeMapperMock;
    
    private JudgeService judgeService;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private JudgeForm judgeForm;
    private Judge judge;
    private JudgeProjection judgeProjection;
    private List<JudgeProjection> judgeList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        judgeService = new JudgeServiceImpl(judgeRepositoryMock, caseRepositoryMock, judgeMapperMock);
        
        judgeForm = new JudgeForm.JudgeFormBuilder().build();
        judge = new Judge.JudgeBuilder().build();
        judgeProjection = factory.createProjection(JudgeProjection.class);
        judgeList = Arrays.asList(judgeProjection);
    }
    
    @Test
    public void save_ShouldMapToFormThenSaveAndReturnId() throws Exception {
        when(judgeMapperMock.toJudge(judgeForm)).thenReturn(judge);
        when(judgeRepositoryMock.save(judge)).thenReturn(judge);
        
        Long result = judgeService.save(judgeForm);
        
        assertThat(result).isEqualTo(judge.getId());
    }
    
    @Test
    public void findById_WithValidIdAndType_ShouldReturnObjectOfMatchingType() throws Exception {
        when(judgeRepositoryMock.findById(1L, JudgeProjection.class)).thenReturn(Optional.of(judgeProjection));
        
        JudgeProjection result = judgeService.findById(1L, JudgeProjection.class);
        
        assertThat(result).isEqualTo(judgeProjection);
    }

    @Test
    public void findFormById_ShouldMapToFormThenReturnForm() throws Exception {
        when(judgeRepositoryMock.findById(1L, Judge.class)).thenReturn(Optional.of(judge));
        when(judgeMapperMock.toJudgeForm(judge)).thenReturn(judgeForm);
        
        JudgeForm result = judgeService.findFormById(1L);
        
        assertThat(result).isEqualTo(judgeForm);
    }
    
    @Test
    public void findAll_ShouldReturnJudgeList() throws Exception {
        when(judgeRepositoryMock.findAllBy(JudgeProjection.class)).thenReturn(judgeList);
        
        List<JudgeProjection> result = judgeService.findAll();
        
        assertThat(result).isEqualTo(judgeList);
    }

    @Test
    public void findAllActive_ShouldReturnJudgeList() throws Exception {
        when(judgeRepositoryMock.findByWorkingStatus(WorkingStatus.ACTIVE, JudgeProjection.class)).thenReturn(judgeList);
        
        List<JudgeProjection> result = judgeService.findAllActive();
        
        assertThat(result).isEqualTo(judgeList);
    }
    
    @Test
    public void delete_ShouldCallDeleteById() throws Exception {
        when(caseRepositoryMock.findByJudge_Id(judgeProjection.getId(), CaseLightProjection.class)).thenReturn(new ArrayList<CaseLightProjection>());
        judgeService.delete(judgeProjection);
        
        verify(judgeRepositoryMock).deleteById(judgeProjection.getId());
    }
    
    @Test
    public void deleteById_ShouldCallDeleteById() throws Exception {
        when(caseRepositoryMock.findByJudge_Id(judgeProjection.getId(), CaseLightProjection.class)).thenReturn(new ArrayList<CaseLightProjection>());
        judgeService.deleteById(1L);
        
        verify(judgeRepositoryMock).deleteById(1L);
    }
}
