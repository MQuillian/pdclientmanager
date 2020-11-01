package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.WorkingStatus;

public class AttorneyRepositoryTest extends AbstractBaseTest{

    @Autowired
    private AttorneyRepository repository;
    
    @Test
    public void findById_WithValidId_ShouldReturnResultOfMatchingType() {
        Optional<Attorney> attorneyResult = repository.findById(1L, Attorney.class);
        Optional<AttorneyProjection> projectionResult = repository.findById(1L, AttorneyProjection.class);
        
        assertThat(attorneyResult.get()).isInstanceOf(Attorney.class);
        assertThat(attorneyResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
    }
    
    @Test
    public void findByInvestigator_Id_ShouldReturnListOfAssociatedAttorneys() throws Exception {
        List<Attorney> result = repository.findByInvestigator_Id(1L, Attorney.class);
        
        assertThat(result.size()).isEqualTo(2);
        Predicate<Attorney> checkInvestigator = attorney ->
            attorney.getInvestigator().getId().equals(1L);
        assertThat(result).allMatch(checkInvestigator);
    }

    @Test
    public void findAllProjectedBy_ShouldReturnListOfAllAttorneysAsProjections() throws Exception {
        List<AttorneyProjection> result = repository.findAllProjectedBy();
        
        assertThat(result.size()).isEqualTo(6);
        Predicate<AttorneyProjection> checkType = attorney ->
            checkIfProjection(attorney);
        assertThat(result).allMatch(checkType);
    }

    @Test
    public void findByWorkingStatus_ShouldReturnListOfMatchingAttorneys() throws Exception {
        List<Attorney> result = repository.findByWorkingStatus(WorkingStatus.ACTIVE, Attorney.class);
        
        assertThat(result.size()).isEqualTo(4);
        Predicate<Attorney> checkWorkingStatus = attorney ->
            attorney.getWorkingStatus().equals(WorkingStatus.ACTIVE);
        assertThat(result).allMatch(checkWorkingStatus);
    }

    @Test
    public void findBy_WithValidType_ShouldReturnListOfAllAttorneysAsMatchingType() throws Exception {
        List<Attorney> attorneyResult = repository.findBy(Attorney.class);
        List<AttorneyProjection> projectionResult = repository.findBy(AttorneyProjection.class);
        
        Predicate<Attorney> checkType = attorney ->
            attorney.getClass().equals(Attorney.class);
        assertThat(attorneyResult.size()).isEqualTo(6);
        assertThat(attorneyResult).allMatch(checkType);
        Predicate<AttorneyProjection> checkProjection = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult.size()).isEqualTo(6);
        assertThat(projectionResult).allMatch(checkProjection);
    }
}
