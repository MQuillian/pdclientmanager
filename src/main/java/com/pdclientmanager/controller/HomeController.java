package com.pdclientmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.repository.DemoDao;

@Controller
public class HomeController {
    
    private DemoDao resetDao;
    
    @Autowired
    public HomeController(DemoDao dao) {
        this.resetDao = dao;
    }
    
    @GetMapping("/")
    public String showHome() {
        return "homePage";
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
    
    @GetMapping("/pendingCourt")
    public String pendingCourt() {
        return "pendingCourt";
    }
    
    @PostMapping("/reset")
    public String resetData(final RedirectAttributes redirectAttributes) {
        resetDao.resetData();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Data reset successfully");
        return "redirect:/";
    }
}
