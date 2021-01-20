package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.google.api.services.calendar.model.Event;
import com.pdclientmanager.calendar.CaseEvent;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(EventMapperDecorator.class)
public interface EventMapper {

    CaseEvent toCaseEventFromEvent(Event googleEvent);
    
    Event toEventFromCaseEvent(CaseEvent event);
    
    List<CaseEvent> toListCaseEventFromListEvent(List<Event> googleEvents);
}
