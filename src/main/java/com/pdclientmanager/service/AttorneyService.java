package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.AttorneyProjection;

public interface AttorneyService {

    Long save(final AttorneyForm form);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    AttorneyForm findFormById(final Long targetId);
        
    List<AttorneyProjection> findAll();
    
    List<AttorneyLightProjection> findAllActive();
    
    boolean delete(final AttorneyProjection dto);
    
    boolean deleteById(final Long targetId);
}
