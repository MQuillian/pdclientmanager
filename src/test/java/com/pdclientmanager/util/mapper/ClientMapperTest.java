package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Client;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ClientMapperTest {
    
    @Autowired
    private ClientMapper mapper;
    
    private Client client;
    private ClientForm clientForm;
    private Case courtCase;
    
    @BeforeEach
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        
        client = new Client.ClientBuilder().build();
        
        courtCase = new Case.CaseBuilder().build();
        client.getCases().add(courtCase);
        
        clientForm = new ClientForm.ClientFormBuilder().build();
    }

    // Mapping between ClientDto and Client entity
    
    @Test
    public void mapper_ShouldMapFormToEntity() {
        
        Client entity = mapper.toClient(clientForm, new CycleAvoidingMappingContext());
        
        assertThat(entity.getId()).isEqualTo(clientForm.getId());
        assertThat(entity.getName()).isEqualTo(clientForm.getName());
        assertThat(entity.getCustodyStatus()).isEqualTo(clientForm.getCustodyStatus());
    }
    
    @Test
    public void mapper_ShouldMapEntityToForm() {
        
        ClientForm form = mapper.toClientForm(client);
        
        assertThat(form.getId()).isEqualTo(client.getId());
        assertThat(form.getName()).isEqualTo(client.getName());
        assertThat(form.getCustodyStatus()).isEqualTo(client.getCustodyStatus());
    }
}