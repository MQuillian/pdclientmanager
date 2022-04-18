package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
import com.pdclientmanager.repository.ClientRepository;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.util.mapper.ClientMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ClientServiceImplTest {
    
    @Mock
    private ClientRepository clientRepositoryMock;
    
    @Mock
    private ClientMapper clientMapperMock;
    
    private ClientService clientService;
    
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    
    private ClientForm clientForm;
    private Client client;
    private ClientProjection clientProjection;
    private ClientLightProjection clientLightProjection;
    private List<ClientLightProjection> clientList;
    
    @BeforeEach
    public void setUp() {

        initMocks(this);
        clientService = new ClientServiceImpl(clientRepositoryMock, clientMapperMock);
        
        clientForm = new ClientForm.ClientFormBuilder().build();
        client = new Client.ClientBuilder().build();
        clientProjection = factory.createProjection(ClientProjection.class);
        clientLightProjection = factory.createProjection(ClientLightProjection.class);
        clientList = Arrays.asList(clientLightProjection);
    }
    
    @Test
    public void save_ShouldMapFormToEntityThenSaveAndReturnId() throws Exception {
        when(clientMapperMock.toClient(eq(clientForm), any(CycleAvoidingMappingContext.class))).thenReturn(client);
        
        Long result = clientService.save(clientForm);
        
        assertThat(result).isEqualTo(client.getId());
        verify(clientRepositoryMock).save(client);
    }
    
    @Test
    public void findById_WithValidIdAndType_ShouldReturnMatchingObject() throws Exception {
        when(clientRepositoryMock.findById(1L, ClientProjection.class)).thenReturn(Optional.of(clientProjection));
        
        ClientProjection result = clientService.findById(1L, ClientProjection.class);
        
        assertThat(result).isEqualTo(clientProjection);
    }
    
    @Test
    public void findFormById_WithValidId_ShouldMapToFormAndReturnForm() throws Exception {
        when(clientRepositoryMock.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapperMock.toClientForm(client)).thenReturn(clientForm);
        
        ClientForm result = clientService.findFormById(1L);
        
        assertThat(result).isEqualTo(clientForm);
    }

    @Test
    public void findByName_WithValidRequest_ShouldReturnClientList() throws Exception {
        when(clientRepositoryMock.findFirst10ByNameContaining("test")).thenReturn(clientList);
        
        List<ClientLightProjection> result = clientService.findByName("test");
        
        assertThat(result).isEqualTo(clientList);
    }

    @Test
    public void findAllBy_WithValidType_ShouldReturnListOfMatchingType() throws Exception {
        when(clientRepositoryMock.findAllBy(ClientLightProjection.class)).thenReturn(clientList);
        
        List<ClientLightProjection> result = clientService.findAllBy(ClientLightProjection.class);
        
        assertThat(result).isEqualTo(clientList);
    }

    @Test
    public void delete_ShouldCallDeleteById() throws Exception {
        clientService.delete(clientProjection);
        
        verify(clientRepositoryMock).deleteById(clientProjection.getId());
    }
    
    @Test
    public void deleteById_ShouldCallDeleteById() throws Exception {
        clientService.deleteById(1L);
        
        verify(clientRepositoryMock).deleteById(1L);
    }
}
