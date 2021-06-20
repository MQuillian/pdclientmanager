package com.pdclientmanager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.repository.DemoDao;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.service.AttorneyService;

@Controller
public class HomeController {
    
    private CalendarService calendarService;
    private AttorneyService attorneyService;
    
    private DemoDao resetDao;
      
    @Autowired
    public HomeController(CalendarService calendarService, AttorneyService attorneyService,
    		DemoDao dao) {
        this.calendarService = calendarService;
        this.attorneyService = attorneyService;
        this.resetDao = dao;
    }
    
    @GetMapping("/")
    public String showHome(Model model) {
        try {
            List<CaseEvent> upcomingEvents = calendarService.getListOfAllEventsForCurrentUser();
            
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
    
    @PostMapping("/reset")
    public String resetData(final RedirectAttributes redirectAttributes) {
        resetDao.resetData();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Data reset successfully");
        return "redirect:/";
    }
}
