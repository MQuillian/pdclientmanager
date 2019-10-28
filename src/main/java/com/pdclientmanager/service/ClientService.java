package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientFormDto;
import com.pdclientmanager.model.entity.CustodyStatus;

public interface ClientService {

Long save(final ClientFormDto formDto);
    
    ClientDto findById(final Long targetId);
    
    ClientFormDto findFormById(final Long targetId);
        
    List<ClientDto> findAll();
    
    void delete(final ClientDto dto);
    
    void deleteById(final Long targetId);
}
