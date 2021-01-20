package com.pdclientmanager.util.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.ExtendedProperties;
import com.google.api.services.calendar.model.EventDateTime;
import com.pdclientmanager.calendar.CaseEvent;

public class EventMapperDecorator implements EventMapper {
    
    @Autowired
    @Qualifier("delegate")
    private EventMapper delegate;
    
    @Override
    public CaseEvent toCaseEventFromEvent(Event googleEvent) {
        CaseEvent result = delegate.toCaseEventFromEvent(googleEvent);
        
        result.setStartTime(toLocalDateTimeFromEventDateTime(googleEvent.getStart()));
        result.setEndTime(toLocalDateTimeFromEventDateTime(googleEvent.getEnd()));
        
        Map<String, String> eventProperties = googleEvent.getExtendedProperties().getPrivate();
        
        result.setCaseNumber(eventProperties.get("caseNumber").toString());
        result.setAttorney(eventProperties.get("attorney").toString());
        
        
        return result;
    }
    
    @Override
    public Event toEventFromCaseEvent(CaseEvent event) {
        Event result = delegate.toEventFromCaseEvent(event);
        
        result.setStart(toEventDateTimeFromLocalDateTime(event.getStartTime()));
        result.setEnd(toEventDateTimeFromLocalDateTime(event.getEndTime()));
        
        Map<String, String> privateProps = new HashMap<>();
        privateProps.put("caseNumber", event.getCaseNumber());
        privateProps.put("attorney", event.getAttorney());
        
        result.setExtendedProperties(new ExtendedProperties());
        result.getExtendedProperties().setPrivate(privateProps);
        
        return result;
    }
    
    @Override
    public List<CaseEvent> toListCaseEventFromListEvent(List<Event> googleEvents) {
        List<CaseEvent> result = new ArrayList<>();
        for(Event e : googleEvents) {
            result.add(toCaseEventFromEvent(e));
        }
        return result;
    }

    private LocalDateTime toLocalDateTimeFromEventDateTime(EventDateTime time) {
        String timeString = time.getDateTime().toString();
        int year = Integer.parseInt(timeString.substring(0, 4));
        int month = Integer.parseInt(timeString.substring(5, 7));
        int day = Integer.parseInt(timeString.substring(8, 10));
        int hour = Integer.parseInt(timeString.substring(11, 13));
        int minute = Integer.parseInt(timeString.substring(14, 16));
        return LocalDateTime.of(year, month, day, hour, minute);
    }
    
    private EventDateTime toEventDateTimeFromLocalDateTime(LocalDateTime time) {
        String formattedTime = time.toString();
        if(formattedTime.length() > 16) {
            formattedTime = formattedTime.substring(0, 16);
        }
        formattedTime = formattedTime.concat(":00.00z");
        return new EventDateTime().setDateTime(DateTime.parseRfc3339(formattedTime));
    }
}
