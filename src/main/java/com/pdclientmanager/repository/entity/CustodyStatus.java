package com.pdclientmanager.repository.entity;

public enum CustodyStatus {
    
    IN_CUSTODY("In custody"),
    OUT_ON_BOND("Out on bond");
    
    // Sets desired display text via constructor to be used in jsp views
    
    private String displayText;
    
    private CustodyStatus(String displayText) {
        this.displayText = displayText;
    }
    
    public String getDisplayText() {
        return displayText;
    }
}
