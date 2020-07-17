package com.pdclientmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.projection.CaseProjection;

public interface CaseService {
    
    Long save(final CaseForm form);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    CaseForm findFormById(final Long targetId);
    
    <T> List<T> findAll(Class<T> type);
    
    <T> Page<T> findAll(Pageable pageable, Class<T> type);
    
    <T> List<T> findAllOpen(Class<T> type);
    
    <T> List<T> findAllOpenWithAttorneyId(final Long targetId, Class<T> type);
    
    <T> Page<T> findAllWithClientName(Pageable pageRequest, final String clientName, Class<T> type);
    
    void delete(final CaseProjection courtCase);
    
    void deleteById(final Long targetId);
    
    void reassignOpenCases(final Long prevId, final Long newId);
    
    void reassignAllCases(final Long prevId, final Long newId);
}
