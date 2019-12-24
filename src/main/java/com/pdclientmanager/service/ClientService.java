package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;

public interface ClientService {

Long save(final ClientMinimalDto formDto);
    
    ClientDto findById(final Long targetId);
    
    ClientMinimalDto findFormById(final Long targetId);
    
    List<ClientDto> findByName(String query);
        
    List<ClientDto> findAll();
    
    void delete(final ClientDto dto);
    
    void deleteById(final Long targetId);
}
