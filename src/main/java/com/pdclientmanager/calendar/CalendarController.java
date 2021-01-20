package com.pdclientmanager.calendar;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String showCalendarManagement() {
        return "calendar/calendarManagement";
    }
    
    @GetMapping("calendar/caseEventForm")
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
                service.addEvent(caseEvent);
                
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Event saved successfully");
            } catch(IOException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error saving event");
            }
            
            return "calendar/calendarManagement";
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
            return "calendar/simultaneousMultiCaseEventForm";
        } else {
            try {
                service.batchAddEvents(caseEventsList.getCaseEvents());
                
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Cases added to calendar");
            } catch(IOException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error adding cases to calendar");
            }
            return "calendar/calendarManagement";
        }
    }
}
