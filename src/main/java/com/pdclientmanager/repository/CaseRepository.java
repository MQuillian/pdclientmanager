package com.pdclientmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pdclientmanager.model.entity.Case;

public interface CaseRepository extends BaseRepository<Case> {
    
    List<Case> findByDateClosedIsNull();
    
    List<Case> findByDateClosedIsNullAndAttorney_Id(final Long attorneyId);
    
    Page<Case> findByClient_NameContaining(final Pageable pageRequest, final String clientName);
}
