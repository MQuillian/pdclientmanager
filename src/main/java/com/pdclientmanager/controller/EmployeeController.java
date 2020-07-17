package com.pdclientmanager.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.model.projection.InvestigatorProjection;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.InvestigatorService;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Controller
public class EmployeeController {
    
    private AttorneyService attorneyService;
    private InvestigatorService investigatorService;
    
    @Autowired
    public EmployeeController(AttorneyService attorneyService,
            InvestigatorService investigatorService, AttorneyMapper attorneyMapper,
            InvestigatorMapper investigatorMapper) {
        this.attorneyService = attorneyService;
        this.investigatorService = investigatorService;
    }
    
    @GetMapping("/employeeManagement")
    public String showEmployeeManagement(Model model) {
        model.addAttribute("activeAttorneys", attorneyService.findAllActive());
        model.addAttribute("activeInvestigators", investigatorService.findAllActive());
        return "employeeManagement";
    }
    
    @GetMapping("/attorneys/add")
    public String showNewAttorneyForm(Model model) {
        AttorneyForm attorney = new AttorneyForm();
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.findAllActive());
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys")
    public String saveAttorney(
            @ModelAttribute("attorneyForm") @Valid AttorneyForm attorneyForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "attorneys/attorneyForm";
        } else {
            Long entityId = attorneyService.save(attorneyForm);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Attorney saved successfully!");
            return "redirect:/attorneys/" + entityId;
        }
    }
    
    @GetMapping("/attorneys")
    public String showAllAttorneys(Model model) {
        model.addAttribute("attorneyList", attorneyService.findAll());
        return "attorneys/listAttorneys";
    }
    
    @GetMapping("/attorneys/{id}")
    public String showAttorney(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {
        
        AttorneyProjection attorney = attorneyService.findById(id, AttorneyProjection.class);
        model.addAttribute("attorney", attorney);
        model.addAttribute("caseload", attorney.getCaseload());
        
        return "attorneys/showAttorney";
    }
    
    @GetMapping("/attorneys/{id}/update")
    public String showUpdateAttorneyForm(@PathVariable("id") Long targetId, Model model) {
        AttorneyForm attorney = attorneyService.findFormById(targetId);
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.findAllActive());
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys/{id}/delete")
    public String deleteAttorneyById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        if(attorneyService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Attorney is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", 
                    "The attorney's active caseload must be reassigned before deleting!");
        }
        return "redirect:/attorneys";
    }
    
    @GetMapping("/investigators/add")
    public String showNewInvestigatorForm(Model model) {
        InvestigatorForm investigator = new InvestigatorForm();
        model.addAttribute("investigatorForm", investigator);
        model.addAttribute("activeAttorneys", attorneyService.findAllActive());
        return "investigators/investigatorForm";
    }
    
    @PostMapping("/investigators")
    public String saveInvestigator(
            @ModelAttribute("investigator") @Valid InvestigatorForm investigatorForm, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {

            return "investigators/investigatorForm";
        } else {
            Long entityId = investigatorService.save(investigatorForm);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Investigator saved successfully!");
            return "redirect:/investigators/" + entityId;
        }
    }
    
    @GetMapping("/investigators")
    public String showAllInvestigators(Model model) {
        model.addAttribute("investigatorList", investigatorService.findAll());
        return "investigators/listInvestigators";
    }
    
    @GetMapping("/investigators/{id}")
    public String showInvestigator(@PathVariable("id") Long targetId, Model model) {

        InvestigatorProjection investigator = investigatorService
            .findById(targetId, InvestigatorProjection.class);
        model.addAttribute("investigator", investigator);
        model.addAttribute("assignedAttorneys", investigator.getAssignedAttorneys());
        return "investigators/showInvestigator";
    }
    
    @GetMapping("/investigators/{id}/update")
    public String showUpdateInvestigatorForm(@PathVariable("id") Long targetId,
            Model model) {
        InvestigatorForm investigator = 
                investigatorService.findFormById(targetId);
        model.addAttribute("investigatorForm", investigator);
        model.addAttribute("activeAttorneys", attorneyService.findAllActive());
        return "investigators/investigatorForm";
    }
    
    @PostMapping("/investigators/{id}/delete")
    public String deleteInvestigatorById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        investigatorService.deleteById(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Investigator is deleted!");
        return "redirect:/investigators";
    }
}
