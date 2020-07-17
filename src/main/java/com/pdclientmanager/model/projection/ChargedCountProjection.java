package com.pdclientmanager.model.projection;

public interface ChargedCountProjection {

    Integer getCountNumber();
    ChargeSummary getCharge();
    
    // setters for testing
    void setCountNumber(Integer number);
    
    interface ChargeSummary {
        
        Long getId();
        String getName();
        String getStatute();
    }
}
