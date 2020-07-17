package com.pdclientmanager.model.projection;

import java.time.LocalDate;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface CaseLightProjection {

    Long getId();
    String getCaseNumber();
    LocalDate getDateOpened();
    LocalDate getDateClosed();
    ClientSummary getClient();
    JudgeSummary getJudge();
    AttorneySummary getAttorney();
    
    // setters for testing
    void setId(Long id);
    void setCaseNumber(String caseNumber);
    void setDateOpened(LocalDate dateOpened);
    void setDateClosed(LocalDate dateClosed);
    
    interface ClientSummary {
        
        Long getId();
        String getName();
        CustodyStatus getCustodyStatus();
    }
    
    interface JudgeSummary {
        
        Long getId();
        String getName();
    }
    
    interface AttorneySummary {
        
        Long getId();
        String getName();
    }
}
