package com.pdclientmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;

public interface CaseService {
    
    Long save(final CaseFormDto formDto);
    
    CaseDto findById(final Long targetId);
    
    CaseFormDto findFormById(final Long targetId);
        
    List<CaseDto> findAll();
    
    Page<CaseDto> findAll(Pageable pageable);
    
    List<CaseDto> findAllOpen();
    
    List<CaseDto> findAllOpenWithAttorneyId(final Long targetId);
    
    void delete(final CaseDto dto);
    
    void deleteById(final Long targetId);
}
