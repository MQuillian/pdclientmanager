package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.model.projection.InvestigatorProjection;

public interface InvestigatorService {

    Long save(final InvestigatorForm formDto);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    InvestigatorForm findFormById(final Long targetId);
        
    List<InvestigatorProjection> findAll();
    
    List<InvestigatorProjection> findAllActive();
    
    void delete(final InvestigatorProjection target);
    
    void deleteById(final Long targetId);
}
