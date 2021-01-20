package com.pdclientmanager.calendar;

import java.util.ArrayList;
import java.util.List;

public class CaseEventsList {

    private List<CaseEvent> caseEvents;
    
    public CaseEventsList() {
        caseEvents = new ArrayList<>();
    }
    
    public List<CaseEvent> getCaseEvents() {
        return caseEvents;
    }
    
    public void setCaseEvents(List<CaseEvent> caseEvents) {
        this.caseEvents = caseEvents;
    }
    
    
}
