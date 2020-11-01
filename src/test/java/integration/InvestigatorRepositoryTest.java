package integration;

import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.pdclientmanager.model.projection.InvestigatorProjection;
import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.repository.entity.Investigator;
import com.pdclientmanager.repository.entity.WorkingStatus;

public class InvestigatorRepositoryTest extends AbstractBaseTest {

    @Autowired
    InvestigatorRepository repository;
    
    @Test
    public void findById_WithValidId_ShouldReturnResultOfMatchingType() throws Exception {
        Optional<Investigator> investigatorResult = repository.findById(1L, Investigator.class);
        Optional<InvestigatorProjection> projectionResult = repository.findById(1L, InvestigatorProjection.class);
        
        assertThat(investigatorResult.get().getId()).isEqualTo(1L);
        assertThat(investigatorResult.get().getClass()).isEqualTo(Investigator.class);
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
    }
    
    @Test
    public void findByWorkingStatus_WithValidQuery_ShouldReturnListOfResultsOfMatchingType() throws Exception {
        List<Investigator> investigatorResults = repository.findByWorkingStatus(WorkingStatus.ACTIVE, Investigator.class);
        List<InvestigatorProjection> projectionResults = repository.findByWorkingStatus(WorkingStatus.ACTIVE, InvestigatorProjection.class);
        
        Predicate<Investigator> checkWorkingStatusAndClass = investigator ->
            investigator.getWorkingStatus().equals(WorkingStatus.ACTIVE) && investigator.getClass().equals(Investigator.class);
        assertThat(investigatorResults).allMatch(checkWorkingStatusAndClass);
        Predicate<InvestigatorProjection> checkWorkingStatusAndProjection = proj ->
            proj.getWorkingStatus().equals(WorkingStatus.ACTIVE) && checkIfProjection(proj);
        assertThat(projectionResults).allMatch(checkWorkingStatusAndProjection);
    }

    @Test
    public void findAllBy_ShouldReturnListOfMatchingType() throws Exception {
        List<Investigator> investigatorResults = repository.findAllBy(Investigator.class);
        List<InvestigatorProjection> projectionResults = repository.findAllBy(InvestigatorProjection.class);
        
        Predicate<Investigator> checkClass = obj ->
            obj.getClass().equals(Investigator.class);
        assertThat(investigatorResults.size()).isEqualTo(3);
        assertThat(investigatorResults).allMatch(checkClass);
        Predicate<InvestigatorProjection> checkProjection = proj ->
            checkIfProjection(proj);
        assertThat(projectionResults.size()).isEqualTo(3);
        assertThat(projectionResults).allMatch(checkProjection);
        
    }
}
