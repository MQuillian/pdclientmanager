package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.model.projection.ChargeProjection;

public interface ChargeService {

    Long save(final ChargeForm form);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    ChargeForm findFormById(final Long targetId);
    
    List<ChargeProjection> findByNameOrStatute(String query);
        
    List<ChargeProjection> findAll();
    
    void delete(final ChargeProjection form);
    
    void deleteById(final Long targetId);
}
