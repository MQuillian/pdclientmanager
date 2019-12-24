package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Case;

public interface CaseRepository extends BaseRepository<Case> {
    
    List<Case> findByDateClosedIsNull();
    
    List<Case> findByDateClosedIsNullAndAttorney_Id(final Long attorneyId);
}
