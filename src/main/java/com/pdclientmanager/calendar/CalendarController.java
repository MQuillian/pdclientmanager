package com.pdclientmanager.calendar;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Lazy
public class CalendarController {
    
    private CalendarService service;
    
    @Autowired
    public CalendarController(CalendarService service) {
        this.service = service;
    }

    @GetMapping("/calendar/management")
    public String showCalendarManagement(Model model) {
        try {
            List<CaseEvent> upcomingEvents = service.sortCaseEventListChronologically(
                    service.getListOfAllEventsForCurrentUser());
            
            model.addAttribute("events", upcomingEvents);
            
            return "calendar/calendarManagement";
        } catch(IOException e) {
            System.out.println("Error fetching upcoming events");
            return "error";
        }
    }
    
    @GetMapping("/calendar/caseEventForm")
    public String showCaseEventForm(Model model) {
        CaseEvent caseEvent = new CaseEvent();
        model.addAttribute("caseEvent", caseEvent);
        return "calendar/caseEventForm";
    }
    
    @PostMapping("/calendar/singleEvent")
    public String saveCaseEvent(@ModelAttribute("caseEvent") @Valid CaseEvent caseEvent,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "calendar/caseEventForm";
        } else {
            try {
            	if(caseEvent.getId() == "") {
            		service.addEvent(caseEvent);
                    
                    redirectAttributes.addFlashAttribute("css", "success");
                    redirectAttributes.addFlashAttribute("msg", "Event saved successfully");
            	} else {
            		service.updateEvent(caseEvent);
            		
            		redirectAttributes.addFlashAttribute("css", "success");
                    redirectAttributes.addFlashAttribute("msg", "Event updated successfully");
            	}
            } catch(IOException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error saving event");
            }
            
            return "redirect:/cases/" + caseEvent.getCaseId();
        }
    }
    
    @GetMapping("/calendar/multiCaseEventForm")
    public String showAddMultipleSimultaneousCaseEventForm(Model model) {
        CaseEventsList caseEventsList = new CaseEventsList();
        model.addAttribute("caseEventsList", caseEventsList);
        return "calendar/simultaneousMultiCaseEventForm";
    }
    
    @PostMapping("/calendar/multipleEvents")
    public String saveMultipleCaseEvents(@ModelAttribute("caseEventsList") @Valid CaseEventsList caseEventsList,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "redirect:/calendar/multiCaseEventForm";
        } else {
            try {
                service.batchAddEvents(caseEventsList.getCaseEvents());
                
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Cases added to calendar");
            } catch(IOException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error adding cases to calendar");
            }
            return "redirect:/calendar/management";
        }
    }
    
    @GetMapping("/calendar/{id}/update")
    public String showUpdateCaseEventForm(@PathVariable("id") String eventId, Model model,
    		final RedirectAttributes redirectAttributes) {
    	try {
    		CaseEvent caseEvent = service.getEventById(eventId);
    		if(caseEvent == null) {
    			throw new EntityNotFoundException("No event exists with id - " + eventId);
    		} else {
    			model.addAttribute("caseEvent", caseEvent);
    			return "calendar/caseEventForm";
    		}
    	} catch(Exception e) {
    		redirectAttributes.addFlashAttribute("css", "danger");
    		redirectAttributes.addFlashAttribute("msg", "Error finding matching event");
    		return "redirect:/calendar/management";
    	}
    }
    
    @PostMapping("/calendar/{id}/delete")
    public String deleteCaseEvent(@PathVariable("id") String eventId, final RedirectAttributes redirectAttributes) {
    	try {
    		service.deleteEvent(eventId);
    		
    		redirectAttributes.addFlashAttribute("css", "success");
    		redirectAttributes.addFlashAttribute("msg", "Event successfully deleted!");
    		
    		return "redirect:/calendar/management";
    	} catch(IOException e) {
    		redirectAttributes.addFlashAttribute("css", "danger");
    		redirectAttributes.addFlashAttribute("msg", "Error deleting event");
    		return "redirect:/calendar/management";
    	}
    }
    
    @PostMapping("/calendar/{id}/deleteFromHome")
    public String deleteCaseEventFromHomePage(@PathVariable("id") String eventId, final RedirectAttributes redirectAttributes) {
        try {
            service.deleteEvent(eventId);
            
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Event successfully deleted!");
            
            return "redirect:/";
        } catch(IOException e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Error deleting event");
            return "redirect:/";
        }
    }
}
