package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.JudgeRepository;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.repository.entity.WorkingStatus;

public class JudgeRepositoryTest extends AbstractBaseTest {

    @Autowired
    JudgeRepository repository;
    
    @Test
    public void findById_WithValidId_ShouldReturnResultOfMatchingType() throws Exception {
        Optional<Judge> judgeResult = repository.findById(1L, Judge.class);
        Optional<JudgeProjection> projectionResult = repository.findById(1L, JudgeProjection.class);
        
        assertThat(judgeResult.get().getClass()).isEqualTo(Judge.class);
        assertThat(judgeResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
    }

    @Test
    public void findAllBy_ShouldReturnListOfMatchingType() throws Exception {
        List<Judge> judgeResult = repository.findAllBy(Judge.class);
        List<JudgeProjection> projectionResult = repository.findAllBy(JudgeProjection.class);
        
        Predicate<Judge> checkClass = judge ->
            judge.getClass().equals(Judge.class);
        assertThat(judgeResult.size()).isEqualTo(3);
        assertThat(judgeResult).allMatch(checkClass);
        Predicate<JudgeProjection> checkProjection = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult.size()).isEqualTo(3);
        assertThat(projectionResult).allMatch(checkProjection);

    }
    
    @Test
    public void findByWorkingStatus_WithValidQuery_ShouldReturnListOfMatchingType() throws Exception {
        List<Judge> judgeResult = repository.findByWorkingStatus(WorkingStatus.ACTIVE, Judge.class);
        List<JudgeProjection> projectionResult = repository.findByWorkingStatus(WorkingStatus.ACTIVE, JudgeProjection.class);
        
        Predicate<Judge> checkClassAndWorkingStatus = judge ->
            judge.getClass().equals(Judge.class) && judge.getWorkingStatus().equals(WorkingStatus.ACTIVE);
        assertThat(judgeResult.size()).isEqualTo(2);
        assertThat(judgeResult).allMatch(checkClassAndWorkingStatus);
        Predicate<JudgeProjection> checkProjectionAndWorkingStatus = proj ->
            checkIfProjection(proj) && proj.getWorkingStatus().equals(WorkingStatus.ACTIVE);
        assertThat(projectionResult.size()).isEqualTo(2);
        assertThat(projectionResult).allMatch(checkProjectionAndWorkingStatus);
    }
}
