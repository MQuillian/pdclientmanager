package com.pdclientmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pdclientmanager.model.Attorney;
import com.pdclientmanager.service.CrudService;

@Controller
public class AttorneyController {
    
    @Autowired
    CrudService<Attorney> attorneyService;
    
    @ModelAttribute("attorney")
    public Attorney formBackingObject() {
        return new Attorney();
    }
    
    @GetMapping("/attorneyManagementHome")
    public String attorneyManagementHome(Model model) {
        model.addAttribute("attorneys", attorneyService.list());
        return "attorneyManagementHome";
    }
    
    @PostMapping("/addAttorney")
    public String saveAttorney(@ModelAttribute("attorney") @Valid Attorney attorney, 
                            BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("attorneys", attorneyService.list());
            return "attorneyManagementHome";
        }
        
        attorneyService.save(attorney);
        return "attorneyManagementHome";
    }
    
    @GetMapping("/searchAttorney")
    public String searchAttorney(@RequestParam("searchTerm") String searchTerm, Model model) {
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("attorneys", attorneyService.search(searchTerm));
        return "attorneySearchResults";
    }
    
    @GetMapping("/manageAttorney/{id}")
    public String manageAttorney(@PathVariable("id") Long targetId, Model model) {
        model.addAttribute("attorney", attorneyService.getById(targetId));
        return "manageAttorney";
    }
    
    @PostMapping("/manageAttorney/updateAttorney")
    public String updateAttorney(@ModelAttribute("attorney") @Valid Attorney attorney,
            BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("attorney", attorney);
            return "manageAttorney";
        }
        attorneyService.update(attorney.getId(), attorney);
        model.addAttribute("attorneys", attorneyService.list());
        return "redirect:/attorneyManagementHome";
    }

}
