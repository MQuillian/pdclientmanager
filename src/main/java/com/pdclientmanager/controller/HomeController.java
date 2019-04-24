package com.pdclientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome() {
        return "homePage";
    }
    
    @GetMapping("/searchPage")
    public String searchPage() {
        return "searchPage";
    }
    
    @GetMapping("/caseManagement")
    public String caseManagement() {
        return "caseManagement";
    }
    
    @GetMapping("/employeeManagement")
    public String employeeManagement() {
        return "employeeManagement";
    }
    
    @GetMapping("/individualStats")
    public String individualStats() {
        return "individualStats";
    }
    
    @GetMapping("/officeStats")
    public String officeStats() {
        return "officeStats";
    }
    
    @GetMapping("/pendingCourt")
    public String pendingCourt() {
        return "pendingCourt";
    }
    
}
