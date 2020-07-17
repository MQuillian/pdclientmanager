package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.repository.entity.Client;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CaseResolver.class)
public interface ClientMapper {
    
    // Mapping between ClientForm and Client entity

    Client toClient(final ClientForm form, @Context CycleAvoidingMappingContext context);
    
    ClientForm toClientForm(final Client entity);
    
    List<Client> toClientList(final List<ClientForm> forms);
    
    List<ClientForm> toClientFormList(final List<Client> entities);
}
