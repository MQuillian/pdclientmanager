package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;

public interface InvestigatorService {

    Long save(final InvestigatorFormDto formDto);
    
    InvestigatorDto findById(final Long targetId);
    
    InvestigatorFormDto findFormById(final Long targetId);
        
    List<InvestigatorDto> findAll();
    
    List<InvestigatorDto> findAllActive();
    
    void delete(final InvestigatorDto dto);
    
    void deleteById(final Long id);
}
