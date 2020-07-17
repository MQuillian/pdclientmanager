package com.pdclientmanager.model.projection;

import com.pdclientmanager.repository.entity.WorkingStatus;

public interface AttorneyLightProjection {

    Long getId();
    String getName();
    WorkingStatus getWorkingStatus();
    InvestigatorSummary getInvestigator();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setWorkingStatus(WorkingStatus status);
    
    interface InvestigatorSummary {
        
        Long getId();
        String getName();
        
        void setId(Long id);
        void setName(String name);
    }
}
