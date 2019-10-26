package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.JudgeDto;

public interface JudgeService {

    Long save(final JudgeDto formDto);
    
    JudgeDto findById(final Long targetId);
        
    List<JudgeDto> findAll();
    
    List<JudgeDto> findAllActive();
    
    void delete(final JudgeDto dto);
    
    void deleteById(final Long targetId);
}
