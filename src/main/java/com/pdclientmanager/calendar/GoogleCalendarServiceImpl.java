package com.pdclientmanager.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.pdclientmanager.service.UserService;
import com.pdclientmanager.util.mapper.EventMapper;

@Service
@Lazy
public class GoogleCalendarServiceImpl implements CalendarService {

    private final String APPLICATION_NAME = "PDCM";
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final NetHttpTransport HTTP_TRANSPORT;
    private GoogleCredentials credentials;
    private HttpRequestInitializer requestInitializer;
    private com.google.api.services.calendar.Calendar calendar;
    
    private EventMapper mapper;
    private UserService userService;
  
    @Autowired
    public GoogleCalendarServiceImpl(EventMapper mapper, UserService userService) throws IOException, GeneralSecurityException {
        this.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        this.credentials = GoogleCredentials.fromStream(
            GoogleCalendarServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH))
            .createScoped(SCOPES);
        credentials.refreshIfExpired();
        requestInitializer = new HttpCredentialsAdapter(credentials);
        calendar = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
            .setApplicationName(APPLICATION_NAME)
            .build();
        
        this.mapper = mapper;
        this.userService = userService;
  }

    public CaseEvent getEventById(String eventId, String calId) throws IOException {
    	Event event = calendar.events().get(calId, eventId).execute();
    	
    	return mapper.toCaseEventFromEvent(event);
    }
    
    public List<CaseEvent> getListOfTwoWeeksEventsForAllEmployees(String calId) throws IOException {
        Events events = calendar.events().list(calId)
            .setTimeMin(getTodaysDateTime())
            .setTimeMax(getTwoWeeksDateTime())
            .execute();
        
        return mapper.toListCaseEventFromListEvent(events.getItems());
    }
    
    public List<CaseEvent> getListOfTwoWeeksEventsForCurrentUser(String calId) throws IOException {
        //If user is ONLY admin then returns events for all employees
        String userSearchTerm = generateUserSearchTerm();
        if(userSearchTerm.contentEquals("ADMIN")) {
            return getListOfTwoWeeksEventsForAllEmployees(calId);
        }
        List<String> searchTerm = new ArrayList<>();
        searchTerm.add(userSearchTerm);
        
        Events events = calendar.events().list(calId)
            .setTimeMin(getTodaysDateTime())
            .setTimeMax(getTwoWeeksDateTime())
            .setPrivateExtendedProperty(searchTerm)
            .execute();
        return mapper.toListCaseEventFromListEvent(events.getItems());
    }
    
    public List<CaseEvent> getListOfAllEventsForCurrentUser(String calId) throws IOException {
        //If user does not have ROLE_ATTORNEY then returns events for all employees
        String userSearchTerm = generateUserSearchTerm();
        if(userSearchTerm.contentEquals("ADMIN")) {
            return getListOfTwoWeeksEventsForAllEmployees(calId);
        }
        List<String> searchTerm = new ArrayList<>();
        searchTerm.add(userSearchTerm);
        
        Events events = calendar.events().list(calId)
            .setPrivateExtendedProperty(searchTerm)
            .execute();
        return mapper.toListCaseEventFromListEvent(events.getItems());
    }
    
    public List<CaseEvent> getListOfAllEventsByCaseNumber(String searchTerm, String calId) throws IOException {
        List<String> searchTerms = new ArrayList<>();
        searchTerms.add("caseNumber=" + searchTerm);
        Events events = calendar.events().list(calId)
            .setPrivateExtendedProperty(searchTerms)
            .execute();
        return mapper.toListCaseEventFromListEvent(events.getItems());
    }
    
    public CaseEvent addEvent(CaseEvent event, String calId) throws IOException {
        Event googleEvent = mapper.toEventFromCaseEvent(event);
        googleEvent = calendar.events().insert(calId, googleEvent).execute();
        return mapper.toCaseEventFromEvent(googleEvent);
    }
    
    public void batchInsertEvents(List<CaseEvent> caseEvents, String calId) throws IOException {
        
        // LIMIT BATCH SIZE TO <50 events DUE TO API CONSTRAINTS
        int currentIndex = 0;
        
        while(currentIndex < caseEvents.size()) {
            executeBatchInsertRequestFromIndex(currentIndex, caseEvents, calId);
            currentIndex += 50;
        }
    }
    
    public CaseEvent updateEvent(CaseEvent event, String calId) throws IOException {
        Event googleEvent = mapper.toEventFromCaseEvent(event);
        calendar.events().update(calId, googleEvent.getId(), googleEvent).execute();
        return event;
    }
    
    public void deleteEvent(CaseEvent event, String calId) throws IOException {
        calendar.events().delete(calId, event.getId()).execute();
    }
    
    public void deleteEvent(String eventId, String calId) throws IOException {
    	calendar.events().delete(calId,  eventId).execute();
    }
    
    @Override
    public CaseEvent getEventById(String eventId) throws IOException {
    	return getEventById(eventId, "primary");
    }
    
    @Override
    public List<CaseEvent> getListOfTwoWeeksEventsForAllEmployees() throws IOException {
        return getListOfTwoWeeksEventsForAllEmployees("primary");
    }
    
    @Override
    public List<CaseEvent> getListOfTwoWeeksEventsForCurrentUser() throws IOException {
        return getListOfTwoWeeksEventsForCurrentUser("primary");
    }

    @Override
    public List<CaseEvent> getListOfAllEventsForCurrentUser() throws IOException {
        return getListOfAllEventsForCurrentUser("primary");
    }

    @Override
    public List<CaseEvent> getListOfAllEventsByCaseNumber(String searchTerm) throws IOException {
        return getListOfAllEventsByCaseNumber(searchTerm, "primary");
    }

    @Override
    public CaseEvent addEvent(CaseEvent event) throws IOException {
        return addEvent(event, "primary");
    }
    
    @Override
    public void batchAddEvents(List<CaseEvent> caseEvents) throws IOException {
        batchInsertEvents(caseEvents, "primary");
    }

    @Override
    public CaseEvent updateEvent(CaseEvent event) throws IOException {
        return updateEvent(event, "primary");
    }

    @Override
    public void deleteEvent(CaseEvent event) throws IOException {
        deleteEvent(event, "primary");
    }
    
    @Override
    public void deleteEvent(String eventId) throws IOException {
    	deleteEvent(eventId, "primary");
    }

    private DateTime getTodaysDateTime() {
        return DateTime.parseRfc3339(LocalDate.now().toString().concat("T00:00:00.00z"));
    }
    
    private DateTime getTwoWeeksDateTime() {
        return DateTime.parseRfc3339(LocalDate.now().plus(2, ChronoUnit.WEEKS).toString().concat("T00:00:00.00z"));
    }
    
    private String generateUserSearchTerm() {
        List<String> roles = userService.getCurrentUserRoles();
        
        if(roles.contains("ROLE_ATTORNEY")) {
            return "attorney=" + userService.getCurrentUserFullName();
        } else {
            return "ADMIN";
        }
    }
    
    private void executeBatchInsertRequestFromIndex(int startIndex, List<CaseEvent> caseEvents,
            String calId) throws IOException {
    	
    	JsonBatchCallback<Event> callback = new JsonBatchCallback<Event>() {

            public void onSuccess(Event event, HttpHeaders responseHeaders) {
            	//NO NEED FOR FURTHER ACTION
            }

            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
            	throw new IOException("Error on calendar batch insert - " + e.getMessage());
            }
        };
    	int endIndex = startIndex + 50;
        if(endIndex > caseEvents.size()) {
            endIndex = caseEvents.size();
        }

        BatchRequest batch = calendar.batch();
        
        for(int i = startIndex; i < endIndex; i++) {
            Event googleEvent = mapper.toEventFromCaseEvent(caseEvents.get(i));
            calendar.events().insert(calId, googleEvent).queue(batch, callback);
        }
        batch.execute();
    }
    
    
    // The below methods are intended solely for use in creating a test calendar
    
    public Calendar createNewCalendar() throws IOException {
        Calendar newCal = new Calendar()
            .setSummary("TEST CAL");
        return calendar.calendars().insert(newCal).execute();
    }
    
    public void deleteCalendar(String id) throws IOException {
        calendar.calendars().delete(id).execute();
    }
    
    public List<CalendarListEntry> getCalendarList() throws IOException {
        return calendar.calendarList().list().execute().getItems();
    }
    
    public List<CaseEvent> getAllEvents(String calId) throws IOException {
        return mapper.toListCaseEventFromListEvent(calendar.events().list(calId).execute().getItems());
    }
}
