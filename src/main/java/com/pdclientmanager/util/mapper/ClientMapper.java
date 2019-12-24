package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.entity.Client;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CaseResolver.class)
public interface ClientMapper {
    
    // Mapping between ClientDto and Client entity

    Client toClient(final ClientDto dto, @Context CycleAvoidingMappingContext context);
    
    ClientDto toClientDto(final Client entity);
    
    List<Client> toClientList(final List<ClientDto> dtos);
    
    List<ClientDto> toClientDtoList(final List<Client> entities);
    
    
    // Mapping between ClientMinimalDto and Client entity
    
    ClientMinimalDto toClientMinimalDto(final Client entity);
    
    Client toClientFromClientMinimalDto(final ClientMinimalDto dto, @Context CycleAvoidingMappingContext context);
    
    List<ClientMinimalDto> toClientMinimalDtoList(final List<Client> entities);
    
    List<Client> toClientListFromClientMinimalDtoList(final List<ClientMinimalDto> dtos);
}
