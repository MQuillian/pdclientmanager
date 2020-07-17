package com.pdclientmanager.model.projection;

import java.util.List;

import com.pdclientmanager.repository.entity.CustodyStatus;
import com.pdclientmanager.repository.entity.WorkingStatus;

public interface InvestigatorProjection {

    Long getId();
    String getName();
    WorkingStatus getWorkingStatus();
    List<AttorneySummary> getAssignedAttorneys();
    
 // setters for testing
    void setId(Long id);
    void setName(String name);
    void setWorkingStatus(WorkingStatus status);
    
    interface AttorneySummary {
        
        Long getId();
        String getName();
    }
}
