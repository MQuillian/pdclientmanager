package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientFormDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Client;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ClientMapperTest {
    
    @Autowired
    @InjectMocks
    private ClientMapper mapper;
    
    @Mock
    private CaseResolver resolver;
    
    private Client client;
    private ClientDto clientDto;
    private ClientFormDto clientFormDto;
    private Case courtCase;
    
    @BeforeEach
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        
        client = new Client.ClientBuilder().build();
        
        clientDto = new ClientDto.ClientDtoBuilder().build();
        
        clientFormDto = new ClientFormDto.ClientFormDtoBuilder().build();
        
        courtCase = new Case.CaseBuilder().build();
    }

    // Mapping between ClientDto and Client entity
    
    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        clientDto.getCases().add(new CaseMinimalDto.CaseMinimalDtoBuilder().build());
        
        Client entity = mapper.toClient(clientDto, new CycleAvoidingMappingContext());
        
        assertThat(clientDto.getId()).isEqualTo(entity.getId());
        assertThat(clientDto.getName()).isEqualTo(entity.getName());
        assertThat(clientDto.getCustodyStatus()).isEqualTo(entity.getCustodyStatus());
        assertThat(clientDto.getCases().get(0).getId()).isEqualTo(entity.getCases().get(0).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        client.getCases().add(new Case.CaseBuilder().build());
        
        ClientDto dto = mapper.toClientDto(client);
        
        assertThat(client.getId()).isEqualTo(dto.getId());
        assertThat(client.getName()).isEqualTo(dto.getName());
        assertThat(client.getCustodyStatus()).isEqualTo(dto.getCustodyStatus());
        assertThat(client.getCases().get(0).getId()).isEqualTo(dto.getCases().get(0).getId());
    }
    
    
    // Mapping between ClientFormDto and Client entity
    
    @Test
    public void mapper_ShouldMapClientFormDtoToEntity() {
        
        clientFormDto.getCasesIds().add(1L);
        
        Mockito.when(resolver.resolve(1L, Case.class)).thenReturn(courtCase);
        
        Client mappedClient = mapper.toClientFromClientFormDto(clientFormDto, new CycleAvoidingMappingContext());

        assertThat(mappedClient.getId()).isEqualTo(clientFormDto.getId());
        assertThat(mappedClient.getName()).isEqualTo(clientFormDto.getName());
        assertThat(mappedClient.getCustodyStatus()).isEqualTo(clientFormDto.getCustodyStatus());
    }
    
    @Test
    public void mapper_ShouldMapEntityToClientFormDto() {
        
        client.getCases().add(courtCase);
        
        Mockito.when(resolver.toLong(courtCase)).thenReturn(courtCase.getId());
        
        ClientFormDto mappedFormDto = mapper.toClientFormDtoFromClient(client);
        
        assertThat(mappedFormDto.getId()).isEqualTo(client.getId());
        assertThat(mappedFormDto.getName()).isEqualTo(client.getName());
        assertThat(mappedFormDto.getCustodyStatus()).isEqualTo(client.getCustodyStatus());
        assertThat(mappedFormDto.getCasesIds().size()).isEqualTo(client.getCases().size());
        assertThat(mappedFormDto.getCasesIds().get(0))
            .isEqualTo(client.getCases().get(0).getId());
    }
    
    
    // Mapping between ClientMinimalDto and Client entity
    
    @Test
    public void mapper_ShouldMapEntityToMinimalDto() {
        
        ClientMinimalDto dto = mapper.toClientMinimalDto(client);
        
        assertThat(client.getId()).isEqualTo(dto.getId());
        assertThat(client.getName()).isEqualTo(dto.getName());
        assertThat(client.getCustodyStatus()).isEqualTo(dto.getCustodyStatus());
    }
}
