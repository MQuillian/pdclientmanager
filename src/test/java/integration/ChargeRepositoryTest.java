package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.repository.entity.Charge;

public class ChargeRepositoryTest extends AbstractBaseTest {
    
    @Autowired
    ChargeRepository repository;
    
    @Test
    public void findById_WithValidId_ShouldReturnChargeAsMatchingType() throws Exception {
        Optional<Charge> chargeResult = repository.findById(1L, Charge.class);
        Optional<ChargeProjection> projectionResult = repository.findById(1L, ChargeProjection.class);
        
        System.out.println(projectionResult.get().getClass());
        assertThat(chargeResult.get().getId()).isEqualTo(1L);
        assertThat(chargeResult.get().getClass()).isEqualTo(Charge.class);
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
    }

    @Test
    public void findAllBy_ShouldReturnFullListOfCharges() throws Exception {
        List<ChargeProjection> result = repository.findAllBy();
        
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void findFirst10ByNameContainingOrStatuteContaining_WithValidNameQueryShouldReturnMatchingList() throws Exception {
        List<ChargeProjection> nameResult = repository.findFirst10ByNameContainingOrStatuteContaining("battery", "battery");
        List<ChargeProjection> statuteResult = repository.findFirst10ByNameContainingOrStatuteContaining("16-8-2", "16-8-2");
        
        Predicate<ChargeProjection> checkIfNameMatches = proj ->
            proj.getName().toLowerCase().contains("battery");
        assertThat(nameResult.size()).isEqualTo(2);
        assertThat(nameResult).allMatch(checkIfNameMatches);
        Predicate<ChargeProjection> checkIfStatuteMatches = proj ->
            proj.getStatute().contentEquals("16-8-2");
        assertThat(statuteResult.size()).isEqualTo(1);
        assertThat(statuteResult).allMatch(checkIfStatuteMatches);
    }
}
