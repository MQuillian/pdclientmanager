package com.pdclientmanager.model.projection;

import java.time.LocalDate;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface CaseStatisticsProjection {

	Long getId();
    ClientSummary getClient();
    
 // setters for testing
    void setId(Long id);
    
    interface ClientSummary {
        
        Long getId();
        String getName();
        CustodyStatus getCustodyStatus();
        LocalDate getIncarcerationDate();
    }
}
