package com.pdclientmanager.model.projection;
import java.time.LocalDate;
import java.util.List;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface ClientProjection {

    Long getId();
    String getName();
    CustodyStatus getCustodyStatus();
    List<CaseSummary> getCases();
    
 // setters for testing
    void setId(Long id);
    void setName(String name);
    void setCustodyStatus(CustodyStatus status);
    
    interface CaseSummary {
        
        Long getId();
        String getCaseNumber();
        LocalDate getDateOpened();
        LocalDate getDateClosed();
        AttorneySummary getAttorney();
        
        interface AttorneySummary {
            
            Long getId();
            String getName();
        }
    }
}
