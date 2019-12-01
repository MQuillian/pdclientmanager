package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.ChargeDto;

public interface ChargeService {

    Long save(final ChargeDto dto);
    
    ChargeDto findById(final Long targetId);
        
    List<ChargeDto> findAll();
    
    List<ChargeDto> findByPartialNameOrStatute(String query);
    
    void delete(final ChargeDto dto);
    
    void deleteById(final Long targetId);
}
