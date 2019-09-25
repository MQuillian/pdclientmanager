package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;

public interface InvestigatorService {

    void persist(final InvestigatorFormDto formDto);
    
    InvestigatorDto getById(final Long targetId);
        
    List<InvestigatorDto> getAll();
    
    List<InvestigatorDto> getAllActive();
    
    void merge(final InvestigatorFormDto dto);
    
    boolean delete(final InvestigatorDto dto);
    
    boolean deleteById(final Long id);
}
