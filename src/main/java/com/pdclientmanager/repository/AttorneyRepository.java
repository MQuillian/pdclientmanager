package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Attorney;

public interface AttorneyRepository extends EmployeeRepository<Attorney> {
    
    List<Attorney> findByInvestigator_Id(Long targetId);
    
}
