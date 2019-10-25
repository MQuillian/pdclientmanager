package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;

public interface AttorneyService {

    Long save(final AttorneyFormDto formDto);
    
    AttorneyDto findById(final Long targetId);
    
    AttorneyFormDto findFormById(final Long targetId);
        
    List<AttorneyDto> findAll();
    
    List<AttorneyDto> findAllActive();
    
    boolean delete(final AttorneyDto dto);
    
    boolean deleteById(final Long id);
}
