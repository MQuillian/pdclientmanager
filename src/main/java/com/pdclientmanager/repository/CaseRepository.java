package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.CaseStatus;

public interface CaseRepository extends BaseRepository<Case> {
    
    List<Case> findByCaseStatus(final CaseStatus status);
    
    List<Case> findByCaseStatusAndAttorney_Id(final CaseStatus status, final Long attorneyId);
}
