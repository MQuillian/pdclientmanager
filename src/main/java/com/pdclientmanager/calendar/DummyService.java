package com.pdclientmanager.calendar;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//@Service("dummy")
public class DummyService implements CalendarService {
    
    private List<CaseEvent> dummyList;
    private List<CaseEvent> shortDummyList;
    private CaseEvent dummyEvent;
    
    public DummyService() {
        LocalDateTime baseDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = baseDateTime.plus(1, ChronoUnit.DAYS);
        LocalDateTime tomorrowPlusOneHour = baseDateTime.plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS);
        LocalDateTime threeWeeks = baseDateTime.plus(3, ChronoUnit.WEEKS);
        LocalDateTime threeWeeksPlusOneHour = baseDateTime.plus(3, ChronoUnit.WEEKS).plus(1, ChronoUnit.DAYS);
        LocalDateTime twoDaysAgo = baseDateTime.minus(2, ChronoUnit.DAYS);
        LocalDateTime twoDaysAgoPlusOneHour = baseDateTime.minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS);
        
        CaseEvent event1 = new CaseEvent(null, "20J0001", "John Schneider",
                "Description 1", "Summary 1", tomorrow, tomorrowPlusOneHour);
        CaseEvent event2 = new CaseEvent(null, "20J0002", "Matt Quillian",
                "Description 2", "Summary 2", tomorrow, tomorrowPlusOneHour);
        CaseEvent event3 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 3", "Summary 3", threeWeeks, threeWeeksPlusOneHour);
        CaseEvent event4 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event5 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event6 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event7 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event8 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event9 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event10 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event11 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        CaseEvent event12 = new CaseEvent(null, "20J0003", "Matt Quillian",
                "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
        
        dummyList = new ArrayList<>();
        dummyList.add(event1);
        dummyList.add(event2);
        dummyList.add(event3);
        dummyList.add(event4);
        dummyList.add(event5);
        dummyList.add(event6);
        dummyList.add(event7);
        dummyList.add(event8);
        dummyList.add(event9);
        dummyList.add(event10);
        dummyList.add(event11);
        dummyList.add(event12);
        
        shortDummyList = new ArrayList<>();
        shortDummyList.add(event1);
        shortDummyList.add(event2);
        shortDummyList.add(event3);
        shortDummyList.add(event4);
        
        dummyEvent = event1;
    }
    

    @Override
    public List<CaseEvent> getListOfTwoWeeksEventsForAllEmployees() throws IOException {
        return shortDummyList;
    }

    @Override
    public List<CaseEvent> getListOfTwoWeeksEventsForCurrentUser() throws IOException {
        return dummyList;
    }

    @Override
    public List<CaseEvent> getListOfAllEventsForCurrentUser() throws IOException {
        return dummyList;
    }

    @Override
    public List<CaseEvent> getListOfAllEventsByCaseNumber(String caseNumber) throws IOException {
        return dummyList;
    }

    @Override
    public CaseEvent addEvent(CaseEvent event) throws IOException {
        return dummyEvent;
    }
    
    @Override
    public void batchAddEvents(List<CaseEvent> caseEvents) throws IOException {
        System.out.println("ADDED BATCH EVENTS");
    }

    @Override
    public CaseEvent updateEvent(CaseEvent event) throws IOException {
        return dummyEvent;
    }

    @Override
    public void deleteEvent(CaseEvent event) throws IOException {
        
    }

}
