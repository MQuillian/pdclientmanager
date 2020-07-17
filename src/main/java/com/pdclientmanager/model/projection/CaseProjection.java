package com.pdclientmanager.model.projection;

import java.time.LocalDate;
import java.util.Map;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface CaseProjection {

    Long getId();
    String getCaseNumber();
    LocalDate getDateOpened();
    LocalDate getDateClosed();
    ClientSummary getClient();
    JudgeSummary getJudge();
    AttorneySummary getAttorney();
    Map<Integer, ChargedCountSummary> getChargedCounts();
    
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
    
    interface ChargedCountSummary {
        
        Integer getCountNumber();
        ChargeSummary getCharge();
        
        interface ChargeSummary {
            
            String getName();
            String getStatute();
        }
    }
}
