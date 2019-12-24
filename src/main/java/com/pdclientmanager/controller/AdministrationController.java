package com.pdclientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrationController {
    
    @GetMapping("/admin")
    public String showAdministrationHome() {
        
        return "/administration/systemManagement";
    }

}
