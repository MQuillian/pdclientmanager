package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;

public interface CaseService {
    
    Long save(final CaseFormDto formDto);
    
    CaseDto findById(final Long targetId);
    
    CaseFormDto findFormById(final Long targetId);
        
    List<CaseDto> findAll();
    
    List<CaseDto> findAllOpen();
    
    List<CaseDto> findAllOpenWithAttorneyId(final Long targetId);
    
    void delete(final CaseDto dto);
    
    void deleteById(final Long targetId);
}
