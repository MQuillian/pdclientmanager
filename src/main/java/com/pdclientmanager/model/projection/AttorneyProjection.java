package com.pdclientmanager.model.projection;

import java.time.LocalDate;
import java.util.List;

import com.pdclientmanager.repository.entity.WorkingStatus;

public interface AttorneyProjection {
        
    Long getId();
    String getName();
    WorkingStatus getWorkingStatus();
    InvestigatorSummary getInvestigator();
    List<CaseSummary> getCaseload();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setWorkingStatus(WorkingStatus status);
    
    interface InvestigatorSummary {
        
        Long getId();
        String getName();
    }
    
    interface CaseSummary {

        Long getId();
        String getCaseNumber();
        ClientSummary getClient();
        LocalDate getDateOpened();
        LocalDate getDateClosed();
        
        interface ClientSummary {
            
            Long getId();
            String getName();
        }
    }
}
