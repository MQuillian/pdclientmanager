package com.pdclientmanager.repository.entity;

public enum WorkingStatus {
    
    ACTIVE("Active"),
    INACTIVE("Inactive");
    
    // Sets desired display text via constructor to be used in jsp views
    
    private String displayText;
    
    private WorkingStatus(String displayText) {
        this.displayText = displayText;
    }
    
    public String getDisplayText() {
        return displayText;
    }
}
