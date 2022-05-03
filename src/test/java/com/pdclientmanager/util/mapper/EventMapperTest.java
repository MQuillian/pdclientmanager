package com.pdclientmanager.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.model.EventDateTime;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.config.WebConfigTest;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfigTest.class})
public class EventMapperTest {

    @Autowired
    private EventMapper mapper;
    
    private Event googleEvent;
    private Event googleEvent2;
    private CaseEvent caseEvent;
    private List<Event> googleEventList;
    
    @BeforeEach
    public void setUp() {
        Map<String, String> eventProperties = new HashMap<>();
        ExtendedProperties properties = new ExtendedProperties();
        eventProperties.put("caseNumber", "01J0001");
        eventProperties.put("attorney", "John Schneider");
        properties.setPrivate(eventProperties);

        DateTime start = new DateTime("2001-02-04T08:30:00-05:00");
        DateTime end = new DateTime("2001-02-04T09:30:00-05:00");
        
        googleEvent = new Event().setExtendedProperties(properties)
            .setId("testId")
            .setDescription("Test description")
            .setSummary("Test summary")
            .setStart(new EventDateTime().setDateTime(start))
            .setEnd(new EventDateTime().setDateTime(end));
        
        googleEvent2 = new Event().setExtendedProperties(properties)
            .setId("test2")
            .setDescription("Test 2")
            .setSummary("Test 2")
            .setStart(new EventDateTime().setDateTime(start))
            .setEnd(new EventDateTime().setDateTime(end));
        
        googleEventList = new ArrayList<>();
        googleEventList.add(googleEvent);
        googleEventList.add(googleEvent2);
        
        LocalDateTime startTime = LocalDateTime.of(2001, 02, 04, 8, 30);
        LocalDateTime endTime = LocalDateTime.of(2001, 02, 04, 9, 30);
        
        caseEvent = new CaseEvent("testID", "01J0001", "1", "John Schneider", "testDescription",
            "testSummary", startTime, endTime);
    }
    
    @Test
    public void mapper_ShouldMapEventToCaseEvent() {
        CaseEvent event = mapper.toCaseEventFromEvent(googleEvent);
        
        assertThat(event.getId()).isEqualTo(googleEvent.getId());
        assertThat(event.getDescription()).isEqualTo(googleEvent.getDescription());
        assertThat(event.getSummary()).isEqualTo(googleEvent.getSummary());
        assertThat(event.getStartTime().toString())
            .isEqualTo(googleEvent.getStart().getDateTime().toString().substring(0, 16));
        assertThat(event.getEndTime().toString())
            .isEqualTo(googleEvent.getEnd().getDateTime().toString().substring(0, 16));
        assertThat(event.getCaseNumber())
            .isEqualTo(googleEvent.getExtendedProperties().getPrivate().get("caseNumber"));
        assertThat(event.getAttorney())
            .isEqualTo(googleEvent.getExtendedProperties().getPrivate().get("attorney"));
    }
    
    @Test
    public void mapper_ShouldMapCaseEventToEvent() {
        Event event = mapper.toEventFromCaseEvent(caseEvent);
        
        assertThat(caseEvent.getId()).isEqualTo(event.getId());
        assertThat(caseEvent.getDescription()).isEqualTo(event.getDescription());
        assertThat(caseEvent.getSummary()).isEqualTo(event.getSummary());
        assertThat(event.getStart().getDateTime().toString().substring(0, 16))
            .isEqualTo(caseEvent.getStartTime().toString());
        assertThat(event.getEnd().getDateTime().toString().substring(0,16))
            .isEqualTo(caseEvent.getEndTime().toString());
        assertThat(caseEvent.getCaseNumber())
            .isEqualTo(event.getExtendedProperties().getPrivate().get("caseNumber"));
        assertThat(event.getExtendedProperties().getPrivate().get("attorney"))
            .isEqualTo("John Schneider");
    }
}
