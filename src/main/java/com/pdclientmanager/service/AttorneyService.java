package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;

public interface AttorneyService {

    Long persist(final AttorneyFormDto formDto);
    
    AttorneyDto getById(final Long targetId);
    
    AttorneyFormDto getFormById(final Long targetId);
        
    List<AttorneyDto> getAll();
    
    List<AttorneyDto> getAllActive();
    
    Long merge(final AttorneyFormDto dto);
    
    boolean delete(final AttorneyDto dto);
    
    boolean deleteById(final Long id);
}
