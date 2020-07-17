package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.model.projection.JudgeProjection;

public interface JudgeService {

    Long save(final JudgeForm form);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    JudgeForm findFormById(final Long targetId);
        
    List<JudgeProjection> findAll();
    
    List<JudgeProjection> findAllActive();
    
    void delete(final JudgeProjection target);
    
    void deleteById(final Long targetId);
}
