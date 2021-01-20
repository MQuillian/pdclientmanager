package com.pdclientmanager.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.calendar.CaseEvent;
import com.pdclientmanager.calendar.GoogleCalendarServiceImpl;
import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.repository.DemoDao;

@Controller
public class HomeController {
    
    private CalendarService calendarService;
    
    private DemoDao resetDao;
      
    @Autowired
    public HomeController(CalendarService calendarService, DemoDao dao) {
        this.calendarService = calendarService;
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
    public String individualStats() {
        return "individualStats";
    }
    
    @GetMapping("/officeStats")
    public String officeStats() {
        return "officeStats";
    }
    
    @PostMapping("/reset")
    public String resetData(final RedirectAttributes redirectAttributes) {
        resetDao.resetData();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Data reset successfully");
        return "redirect:/";
    }
}
