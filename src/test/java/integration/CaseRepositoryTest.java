package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.entity.Case;

public class CaseRepositoryTest extends AbstractBaseTest {
    
    @Autowired
    CaseRepository repository;
    
    @Test
    public void findById_WithValidId_ShouldReturnCaseOfMatchingType() throws Exception {
        Optional<Case> caseResult = repository.findById(1L, Case.class);
        Optional<CaseProjection> projectionResult = repository.findById(1L, CaseProjection.class);
        
        assertThat(caseResult.get().getId()).isEqualTo(1L);
        assertThat(caseResult.get()).isInstanceOf(Case.class);
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
    }

    @Test
    public void findByDateClosedIsNull_ShouldReturnAllUnclosedCasesAsMatchingType() throws Exception {
        List<Case> caseResult = repository.findByDateClosedIsNull(Case.class);
        List<CaseProjection> projectionResult = repository.findByDateClosedIsNull(CaseProjection.class);
        
        Predicate<Case> checkCaseDateClosed = courtCase ->
            courtCase.getDateClosed() == null;
        Predicate<Case> checkCaseType = courtCase ->
            courtCase.getClass().equals(Case.class);
        assertThat(caseResult).isNotEmpty();
        assertThat(caseResult).allMatch(checkCaseDateClosed);
        assertThat(caseResult).allMatch(checkCaseType);
        Predicate<CaseProjection> checkProjectionDateClosed = proj ->
            proj.getDateClosed() == null;
        Predicate<CaseProjection> checkProjectionType = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult).isNotEmpty();
        assertThat(projectionResult).allMatch(checkProjectionDateClosed);
        assertThat(projectionResult).allMatch(checkProjectionType);
    }

    @Test
    public void findProjectionWithChargedCountsByClient_Id_WithValidId_ShouldReturnPageWithMatchingCases() throws Exception {
        Pageable request = PageRequest.of(0, 5);
        
        Page<CaseProjection> result = repository.findProjectionsWithChargedCountsByClient_Id(6L, request);
        
        Predicate<CaseProjection> checkClientId = proj ->
            proj.getClient().getId().equals(6L);
        Predicate<CaseProjection> checkChargedCountsNotEmpty = proj ->
            !proj.getChargedCounts().isEmpty();
        assertThat(result.getNumberOfElements()).isEqualTo(2);
        assertThat(result).allMatch(checkClientId);
        assertThat(result).allMatch(checkChargedCountsNotEmpty);
    }

    @Test
    public void findProjectionsWithChargedCountsByAttorney_Id_WithValidId_ShouldReturnPageWithMatchingCases() throws Exception {
        Pageable request = PageRequest.of(0, 5);
        
        Page<CaseProjection> result = repository.findProjectionsWithChargedCountsByAttorney_Id(1L, request);
        
        Predicate<CaseProjection> checkAttorneyId = proj ->
            proj.getAttorney().getId().equals(1L);
        Predicate<CaseProjection> checkChargedCountsNotEmpty = proj ->
            !proj.getChargedCounts().isEmpty();
        assertThat(result.getNumberOfElements()).isEqualTo(5);
        assertThat(result).allMatch(checkAttorneyId);
        assertThat(result).allMatch(checkChargedCountsNotEmpty);
    }

    @Test
    public void findAllBy_WithValidType_ShouldReturnListOfAllCasesAsMatchingType() throws Exception {
        List<Case> caseResult = repository.findAllBy(Case.class);
        List<CaseProjection> projectionResult = repository.findAllBy(CaseProjection.class);
        
        Predicate<Case> checkCaseType = courtCase ->
            courtCase.getClass().equals(Case.class);
        assertThat(caseResult.size()).isEqualTo(12);
        assertThat(caseResult).allMatch(checkCaseType);
        Predicate<CaseProjection> checkProjectionType = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult.size()).isEqualTo(12);
        assertThat(projectionResult).allMatch(checkProjectionType);
    }

    @Test
    public void findAllBy_WithValidPageRequest_ShouldReturnPageofMatchingSizeAndType() throws Exception {
        Pageable request = PageRequest.of(0,5);
        
        Page<Case> caseResult = repository.findAllBy(request, Case.class);
        Page<CaseProjection> projectionResult = repository.findAllBy(request, CaseProjection.class);
        
        Predicate<Case> checkCaseType = courtCase ->
            courtCase.getClass().equals(Case.class);
        assertThat(caseResult.getNumberOfElements()).isEqualTo(5);
        assertThat(caseResult).allMatch(checkCaseType);
        Predicate<CaseProjection> checkProjectionType = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult.getNumberOfElements()).isEqualTo(5);
        assertThat(projectionResult).allMatch(checkProjectionType);
    }

    @Test
    public void findByAttorney_Id_WithValidId_ShouldReturnListOfAttorneysCasesAsMatchingType() throws Exception {
        List<Case> caseResult = repository.findByAttorney_Id(1L, Case.class);
        List<CaseProjection> projectionResult = repository.findByAttorney_Id(1L, CaseProjection.class);
        
        Predicate<Case> checkCase = courtCase ->
            courtCase.getClass().equals(Case.class) && courtCase.getAttorney().getId().equals(1L);
        assertThat(caseResult.size()).isEqualTo(6);
        assertThat(caseResult).allMatch(checkCase);
        Predicate<CaseProjection> checkProjection = proj ->
            checkIfProjection(proj) && proj.getAttorney().getId().equals(1L);;
        assertThat(projectionResult.size()).isEqualTo(6);
        assertThat(projectionResult).allMatch(checkProjection);
    }

    @Test
    public void findByDateClosedIsNullAndAttorney_Id_WithValidIdAndType_ShouldReturnListOfAttorneysUnclosedCasesOfMatchingType() throws Exception {
        List<Case> caseResult = repository.findByDateClosedIsNullAndAttorney_Id(1L, Case.class);
        List<CaseProjection> projectionResult = repository.findByDateClosedIsNullAndAttorney_Id(1L, CaseProjection.class);
        
        Predicate<Case> checkCase = courtCase ->
            courtCase.getClass().equals(Case.class) && courtCase.getDateClosed() == null;
        assertThat(caseResult.size()).isEqualTo(3);
        assertThat(caseResult).allMatch(checkCase);
        Predicate<CaseProjection> checkProjection = proj ->
            checkIfProjection(proj) && proj.getDateClosed() == null;
        assertThat(projectionResult.size()).isEqualTo(3);
        assertThat(projectionResult).allMatch(checkProjection);
    }
    
    @Test
    public void findFirst10ByDateClosedIsNullAndCaseNumberContaining_WithValidNumberAndType_ShouldReturnListOfMatchingCases() throws Exception {
        List<CaseLightProjection> projectionResult = repository.findFirst10ByDateClosedIsNullAndCaseNumberContaining("18J1", CaseLightProjection.class);
        
        Predicate<CaseLightProjection> checkProjection = proj ->
            checkIfProjection(proj) && proj.getDateClosed() == null
                && proj.getCaseNumber().contains("18J1");
        assertThat(projectionResult.size()).isEqualTo(3);
        assertThat(projectionResult).allMatch(checkProjection);
    }

    @Test
    public void findByClient_NameContaining_WithValidRequestAndName_ShouldReturnPageOfCasesForMatchingClients() throws Exception {
        Pageable request = PageRequest.of(0,5);
        
        Page<Case> caseResult = repository.findByClient_NameContaining(request, "Eric", Case.class);
        Page<CaseProjection> projectionResult = repository.findByClient_NameContaining(request, "Eric", CaseProjection.class);
        
        Predicate<Case> checkCase = courtCase ->
            courtCase.getClass().getName().contains("entity.Case") && courtCase.getClient().getName().contains("Eric");
        assertThat(caseResult.getNumberOfElements()).isEqualTo(3);
        assertThat(caseResult.getContent()).allMatch(checkCase);
        Predicate<CaseProjection> checkProjection = proj ->
            checkIfProjection(proj) && proj.getClient().getName().contains("Eric");
        assertThat(projectionResult.getNumberOfElements()).isEqualTo(3);
        assertThat(projectionResult.getContent()).allMatch(checkProjection);
    }
}
