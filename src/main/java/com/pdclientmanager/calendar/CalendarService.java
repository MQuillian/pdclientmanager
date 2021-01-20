package com.pdclientmanager.calendar;

import java.io.IOException;
import java.util.List;

public interface CalendarService {
    
    public List<CaseEvent> getListOfTwoWeeksEventsForAllEmployees() throws IOException;
    
    public List<CaseEvent> getListOfTwoWeeksEventsForCurrentUser() throws IOException;
        
    public List<CaseEvent> getListOfAllEventsForCurrentUser() throws IOException;
        
    public List<CaseEvent> getListOfAllEventsByCaseNumber(String caseNumber) throws IOException;
    
    public CaseEvent addEvent(CaseEvent event) throws IOException;
    
    public void batchAddEvents(List<CaseEvent> caseEvents) throws IOException;
    
    public CaseEvent updateEvent(CaseEvent event) throws IOException;
    
    public void deleteEvent(CaseEvent event) throws IOException;
}
