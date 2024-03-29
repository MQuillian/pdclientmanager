package com.pdclientmanager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.service.AttorneyService;

@Controller
public class HomeController {
    
    private CalendarService calendarService;
    private AttorneyService attorneyService;
      
    @Autowired
    public HomeController(CalendarService calendarService, AttorneyService attorneyService) {
        this.calendarService = calendarService;
        this.attorneyService = attorneyService;
    }
    
    @GetMapping("/")
    public String showHome(Model model) {
        try {
            List<CaseEvent> upcomingEvents = calendarService.sortCaseEventListChronologically(
                    calendarService.getListOfAllEventsForCurrentUser());
            
            model.addAttribute("events", upcomingEvents);
            
            return "homePage";
        } catch(IOException e) {
            System.out.println("Error fetching upcoming events");
            return "error";
        }
    }
    
    @GetMapping("/searchPage")
    public String searchPage() {
        return "searchPage";
    }
    
    @GetMapping("/individualStats")
    public String individualStats(Model model) {
    	List<AttorneyLightProjection> activeAttorneys = attorneyService.findAllActive();
        model.addAttribute("activeAttorneys", activeAttorneys);
    	return "individualStats";
    }
}
