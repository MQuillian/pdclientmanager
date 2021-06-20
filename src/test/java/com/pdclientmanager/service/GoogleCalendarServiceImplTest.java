package com.pdclientmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.calendar.GoogleCalendarServiceImpl;
import com.pdclientmanager.config.WebConfigTest;
import com.pdclientmanager.security.UserService;
import com.pdclientmanager.util.mapper.EventMapper;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
@TestInstance(Lifecycle.PER_CLASS)
public class GoogleCalendarServiceImplTest {

    @Mock
    private UserService userServiceMock;
    
    @Autowired
    private EventMapper mapper;
    
    private GoogleCalendarServiceImpl service;
    
    LocalDateTime baseDateTime = LocalDateTime.now();
    LocalDateTime baseDateTimePlusTwoWeeks = baseDateTime.plus(2, ChronoUnit.WEEKS);
    
    private String testCalId;
    private CaseEvent event1;
    private CaseEvent event2;
    private CaseEvent event3;
    private CaseEvent event4;
    
    private List<String> attyRole;
    
    @BeforeAll
    public void setup() {
        initMocks(this);
        
        try {
            service = new GoogleCalendarServiceImpl(mapper, userServiceMock);

            testCalId = service.createNewCalendar().getId();
            
            LocalDateTime tomorrow = baseDateTime.plus(1, ChronoUnit.DAYS);
            LocalDateTime tomorrowPlusOneHour = baseDateTime.plus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS);
            LocalDateTime threeWeeks = baseDateTime.plus(3, ChronoUnit.WEEKS);
            LocalDateTime threeWeeksPlusOneHour = baseDateTime.plus(3, ChronoUnit.WEEKS).plus(1, ChronoUnit.DAYS);
            LocalDateTime twoDaysAgo = baseDateTime.minus(2, ChronoUnit.DAYS);
            LocalDateTime twoDaysAgoPlusOneHour = baseDateTime.minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS);
            
            event1 = new CaseEvent(null, "20J0001", "John Schneider",
                    "Description 1", "Summary 1", tomorrow, tomorrowPlusOneHour);
            event2 = new CaseEvent(null, "20J0002", "Matt Quillian",
                    "Description 2", "Summary 2", tomorrow, tomorrowPlusOneHour);
            event3 = new CaseEvent(null, "20J0003", "Matt Quillian",
                    "Description 3", "Summary 3", threeWeeks, threeWeeksPlusOneHour);
            event4 = new CaseEvent(null, "20J0003", "Matt Quillian",
                    "Description 4", "Summary 4", twoDaysAgo, twoDaysAgoPlusOneHour);
            
            service.addEvent(event1, testCalId);
            service.addEvent(event2, testCalId);
            service.addEvent(event3, testCalId);
            service.addEvent(event4, testCalId);
            
            attyRole = new ArrayList<>();
            attyRole.add("ROLE_ATTORNEY");
     
        } catch(Exception e) {
            System.out.println("Exception in setup- " + e.getMessage());
        }
    }
    
    @AfterAll
    public void cleanup() {
    	try {
    		service.deleteCalendar(testCalId);
    	} catch(IOException e) {
    		System.out.println("ERROR CLEANING UP AFTER GOOGLECALENDARSERVICEIMPL TESTS");
    	}
    }
    
    @Test
    public void getListOfTwoWeeksEventsForAllEmployees_ShouldReturnListOfEventsInTimeframe() {
        try {
            List<CaseEvent> result = service.getListOfTwoWeeksEventsForAllEmployees(testCalId);
            Predicate<CaseEvent> datesAreWithinTwoWeeks = event ->
                event.getStartTime().isAfter(baseDateTime) && event.getStartTime().isBefore(baseDateTimePlusTwoWeeks)
                    && event.getEndTime().isAfter(baseDateTime) && event.getEndTime().isBefore(baseDateTimePlusTwoWeeks);
            
            assertThat(result.size()).isEqualTo(2);
            assertThat(result).allMatch(datesAreWithinTwoWeeks);

        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }
    
    @Test
    public void getListOfTwoWeeksEventsForCurrentUser_WithAttorneyUser_ShouldReturnListOfMatchingAttorneyEvents() {
        try {
            when(userServiceMock.getCurrentUserRoles()).thenReturn(attyRole);
            when(userServiceMock.getCurrentUserFullName()).thenReturn("Matt Quillian");
            
            List<CaseEvent> result = service.getListOfTwoWeeksEventsForCurrentUser(testCalId);
            CaseEvent resultEvent = result.get(0);
            
            assertThat(result.size()).isEqualTo(1);
            assertThat(resultEvent.getAttorney()).isEqualTo("Matt Quillian");
            assertThat(resultEvent.getStartTime().isAfter(baseDateTime));
            assertThat(resultEvent.getStartTime().isBefore(baseDateTimePlusTwoWeeks));
            assertThat(resultEvent.getEndTime().isAfter(baseDateTime));
            assertThat(resultEvent.getEndTime().isBefore(baseDateTimePlusTwoWeeks));
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }

    @Test
    public void getListOfAllEventsForCurrentUser_WithAttorneyUser_ShouldReturnListOfAttorneyEvents() {
        try {
            when(userServiceMock.getCurrentUserRoles()).thenReturn(attyRole);
            when(userServiceMock.getCurrentUserFullName()).thenReturn("Matt Quillian");
            
            List<CaseEvent> result = service.getListOfAllEventsForCurrentUser(testCalId);
            
            Predicate<CaseEvent> nameMatchesSearchTerm = event ->
                event.getAttorney().contentEquals("Matt Quillian");
            
            assertThat(result.size()).isEqualTo(3);
            assertThat(result).allMatch(nameMatchesSearchTerm);
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }
    
    @Test
    public void getListOfAllEventsByCaseNumber_ShouldReturnListOfMatchingEvents() {
        try {
            List<CaseEvent> result = service.getListOfAllEventsByCaseNumber(event3.getCaseNumber(), testCalId);
            
            Predicate<CaseEvent> caseNumberMatchesSearchTerm = event ->
                event.getCaseNumber().contentEquals(event3.getCaseNumber());
                
            assertThat(result.size()).isEqualTo(2);
            assertThat(result).allMatch(caseNumberMatchesSearchTerm);
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }
    
    @Test
    public void addEvent_ShouldAddEventToCalendar() {
        try {
            CaseEvent newEvent = new CaseEvent(null, "20J0006", "Matt Quillian",
                    "Description", "Summary", baseDateTime,
                    baseDateTime.plus(1, ChronoUnit.HOURS));
            
            CaseEvent result = service.addEvent(newEvent, testCalId);
            
            assertThat(result.getId()).isNotNull();
            assertThat(service.getListOfAllEventsByCaseNumber("20J0006", testCalId))
                .isNotEmpty();
            
            //Cleanup
            service.deleteEvent(result, testCalId);
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }
    
    @Test
    public void batchAddEvents_ShouldAddEventsToCalendar() {
        List<CaseEvent> caseEvents = new ArrayList<>();
        for(int i = 0; i < 60; i++) {
            caseEvents.add(new CaseEvent(null, "batchCaseNumber", "BatchTest Attorney",
                    "Test Description" + Integer.toString(i), null, baseDateTime, baseDateTime.plus(1, ChronoUnit.HOURS)));
        }
        try {
            service.batchInsertEvents(caseEvents, testCalId);
            List<CaseEvent> result = service.getListOfAllEventsByCaseNumber("batchCaseNumber", testCalId);

            assertThat(result.size()).isEqualTo(caseEvents.size());
            
            //Cleanup
            for(CaseEvent ev : service.getListOfAllEventsByCaseNumber("batchCaseNumber", testCalId)) {
            	service.deleteEvent(ev, testCalId);
            }
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }

    @Test
    public void updateEvent_ShouldUpdateEventInCalendar() {
        try {
            CaseEvent originalEvent = service.getListOfAllEventsByCaseNumber(event1.getCaseNumber(), testCalId).get(0);
            CaseEvent updatedEvent = new CaseEvent(originalEvent.getId(), "20J0010", "Matt Quillian",
                    "Description", "Summary", baseDateTime,
                    baseDateTime.plus(1, ChronoUnit.HOURS));
            
            CaseEvent result = service.updateEvent(updatedEvent, testCalId);
            
            assertThat(result.getId()).isEqualTo(originalEvent.getId());
            assertThat(result.getCaseNumber()).isEqualTo(updatedEvent.getCaseNumber());
            assertThat(result.getAttorney()).isEqualTo(updatedEvent.getAttorney());
            assertThat(result.getDescription()).isEqualTo(updatedEvent.getDescription());
            assertThat(result.getSummary()).isEqualTo(updatedEvent.getSummary());
            assertThat(result.getStartTime()).isEqualTo(updatedEvent.getStartTime());
            assertThat(result.getEndTime()).isEqualTo(updatedEvent.getEndTime());
            
            //Cleanup
            service.deleteEvent(updatedEvent, testCalId);
            service.addEvent(event1, testCalId);
            
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }

    @Test
    public void deleteEvent_ShouldDeleteEventFromCalendar() {
        try {
            CaseEvent target = service.getListOfAllEventsByCaseNumber(event1.getCaseNumber(), testCalId).get(0);

            service.deleteEvent(target, testCalId);
            
            assertThat(service.getListOfAllEventsByCaseNumber(event1.getCaseNumber(), testCalId))
                .isEmpty();
            
            //Cleanup
            service.addEvent(event1, testCalId);
        } catch(IOException e) {
            fail("IOException was thrown - " + e.getMessage());
        }
    }
}
