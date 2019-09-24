package com.pdclientmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.service.CaseService;

@Controller
public class CaseController {
    
    private CaseService caseService;
    
    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }
    
    @GetMapping("/cases")
    public String showAllCases(Model model) {
        model.addAttribute("cases", caseService.getAllWithInitializedClients());
        return "cases/listCases";
    }
    
    @GetMapping("/cases/{id}")
    public String showCase(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {
        CaseDto courtCase = caseService.getById(id);
        if(courtCase == null) {
            redirectAttributes.addAttribute("css", "danger");
            redirectAttributes.addAttribute("msg", "Case could not be found...");
            return "redirect:/cases";
        }
        model.addAttribute("case", courtCase);
        return "cases/showCase";
    }
}
