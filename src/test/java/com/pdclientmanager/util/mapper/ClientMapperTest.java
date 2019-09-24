package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Client;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class ClientMapperTest {
    
    @Autowired
    ClientMapper clientMapper;

    @Test
    public void mapper_ShouldMapDtoToEntity() {
        
        ClientDto dto = new ClientDto.ClientDtoBuilder()
                .build();
        dto.getCases().add(new CaseMinimalDto.CaseMinimalDtoBuilder().build());
        
        Client entity = clientMapper.toClient(dto, new CycleAvoidingMappingContext());
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCustodyStatus()).isEqualTo(entity.getCustodyStatus());
        assertThat(dto.getCases().get(0).getId()).isEqualTo(entity.getCases().get(0).getId());
    }
    
    @Test
    public void mapper_ShouldMapEntityToDto() {
        
        Client entity = new Client.ClientBuilder()
                .build();
        entity.getCases().add(new Case.CaseBuilder().build());
        
        ClientDto dto = clientMapper.toClientDto(entity, new CycleAvoidingMappingContext());
        
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getCustodyStatus()).isEqualTo(dto.getCustodyStatus());
        assertThat(entity.getCases().get(0).getId()).isEqualTo(dto.getCases().get(0).getId());
    }
}
