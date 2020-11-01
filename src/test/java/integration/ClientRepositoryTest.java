package integration;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
import com.pdclientmanager.repository.ClientRepository;
import com.pdclientmanager.repository.entity.Client;

public class ClientRepositoryTest extends AbstractBaseTest {

    @Autowired
    ClientRepository repository;
    
    @Test
    public void findById_WithValidId_ReturnsEntityOfMatchingType() throws Exception {
        Optional<Client> clientResult = repository.findById(1L, Client.class);
        Optional<ClientProjection> projectionResult = repository.findById(1L, ClientProjection.class);
        
        assertThat(clientResult.get().getId()).isEqualTo(1L);
        assertThat(clientResult.get().getClass()).isEqualTo(Client.class);
        assertThat(projectionResult.get().getId()).isEqualTo(1L);
        assertThat(checkIfProjection(projectionResult.get())).isTrue();
    }

    @Test
    public void findAllBy_ShouldReturnListOfMatchingType() throws Exception {
        List<Client> clientResult = repository.findAllBy(Client.class);
        List<ClientProjection> projectionResult = repository.findAllBy(ClientProjection.class);
        
        Predicate<Client> checkIfClient = client ->
            client.getClass().equals(Client.class);
        assertThat(clientResult.size()).isEqualTo(10);
        assertThat(clientResult).allMatch(checkIfClient);
        Predicate<ClientProjection> checkIfProjection = proj ->
            checkIfProjection(proj);
        assertThat(projectionResult.size()).isEqualTo(10);
        assertThat(projectionResult).allMatch(checkIfProjection);

    }

    @Test
    public void findFirst10ByNameContaining_WithValidQuery_ShouldReturnListOfMatchingNames() throws Exception {
        List<ClientLightProjection> result = repository.findFirst10ByNameContaining("Eric");
        
        Predicate<ClientLightProjection> checkName = client ->
            client.getName().contains("Eric");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).allMatch(checkName);
    }
}
