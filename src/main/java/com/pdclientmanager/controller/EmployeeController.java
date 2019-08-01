package com.pdclientmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.Attorney;
import com.pdclientmanager.model.AttorneyEditor;
import com.pdclientmanager.model.Case;
import com.pdclientmanager.model.Investigator;
import com.pdclientmanager.model.InvestigatorEditor;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.EmployeeService;

@Controller
public class EmployeeController {
    
    @Autowired
    EmployeeService<Attorney> attorneyService;
    
    @Autowired
    EmployeeService<Investigator> investigatorService;
    
    @Autowired
    CaseService caseService;
    
    @InitBinder 
    public void initBinder(WebDataBinder b) {
        b.registerCustomEditor(Attorney.class, new AttorneyEditor(attorneyService));
        b.registerCustomEditor(Investigator.class, 
                new InvestigatorEditor(investigatorService));
    } 
    
    @GetMapping("/employeeManagement")
    public String employeeManagement(Model model) {
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "employeeManagement";
    }
    
    @GetMapping("/attorneys/add")
    public String showNewAttorneyForm(Model model) {
        Attorney attorney = new Attorney();
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys")
    public String persistOrMergeAttorney(
            @ModelAttribute("attorneyForm") @Valid Attorney attorney, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "attorneys/attorneyForm";
        } else {
            if(attorney.isNew()) {
                attorneyService.persist(attorney);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Attorney added successfully!");
            } else {
                attorneyService.merge(attorney);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Attorney updated successfully!");
            }
            return "redirect:/attorneys/" + attorney.getId();
        }
    }
    
    @GetMapping("/attorneys")
    public String showAllAttorneys(Model model) {
        model.addAttribute("attorneyList", attorneyService.getAll());
        return "attorneys/list";
    }
    
    @GetMapping("/attorneys/{id}")
    public String showAttorney(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {
        Attorney attorney = attorneyService.getById(id);
        if(attorney == null) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Attorney could not be found...");
            return "redirect:/attorneys";
        }
        List<Case> caseload = caseService.getAllActiveByAttorneyIdWithInitializedClient(id);
        for(Case courtCase : caseload) {
            Hibernate.initialize(courtCase.getClient());
        }
        model.addAttribute("attorney", attorney);
        model.addAttribute("caseload", caseload);
        //INITIALIZE THAT SHIT YO
        return "attorneys/showIndividual";
    }
    
    @GetMapping("/attorneys/{id}/update")
    public String showUpdateAttorneyForm(@PathVariable("id") Long targetId, Model model) {
        Attorney attorney = attorneyService.getById(targetId);
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
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
                    "The attorney's caseload must be reassigned before deleting!");
        }
        return "redirect:/attorneys";
    }
    
    @GetMapping("/investigators/add")
    public String showNewInvestigatorForm(Model model) {
        Investigator investigator = new Investigator();
        model.addAttribute("investigatorForm", investigator);
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        return "investigators/investigatorForm";
    }
    
    @PostMapping("/investigators")
    public String persistOrMergeInvestigator(
            @ModelAttribute("investigator") @Valid Investigator investigator, 
            BindingResult result, Model model) {
        if(result.hasErrors()) {
        return "addInvestigatorForm";
        } else {
            for(Attorney attorney : investigator.getAssignedAttorneys()) {
                attorney.setInvestigator(investigator);
                attorneyService.merge(attorney);
            }
            if(investigator.isNew()) {
                investigatorService.persist(investigator);
            } else {
                investigatorService.merge(investigator);
            }
        }
        
        return "redirect:/employeeManagement";
    }
    
    @GetMapping("/investigators")
    public String viewAllInvestigators(Model model) {
        model.addAttribute("investigatorList", investigatorService.getAll());
        return "investigators/list";
    }
    
    @GetMapping("/investigators/{id}")
    public String showInvestigator(@PathVariable("id") Long targetId, Model model) {
        Investigator investigator = investigatorService
                .getByIdWithInitializedAssignedAttorneys(targetId);
        if(investigator == null) {
            model.addAttribute("css", "error");
            model.addAttribute("msg", "Investigator not found");
            return "redirect:/ivestigatorss";
        }
        Hibernate.initialize(investigator.getAssignedAttorneys());
        model.addAttribute("investigator", investigator);
        model.addAttribute("assignedAttorneys", investigator.getAssignedAttorneys());
        return "investigators/showIndividual";
    }
    
    @GetMapping("/investigators/{id}/update")
    public String showUpdateInvestigatorForm(@PathVariable("id") Long targetId, Model model,
            final RedirectAttributes redirectAttributes) {
        Investigator investigator = 
                investigatorService.getByIdWithInitializedAssignedAttorneys(targetId);
        if(investigator == null) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Investigator could not be found...");
            return "redirect:/investigators";
        }
        model.addAttribute("investigatorForm", investigator);
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        return "investigators/investigatorForm";
    }
    
    @PostMapping("/investigators/{id}/delete")
    public String deleteInvestigatorById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        if(investigatorService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Investigator is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg",
                    "An error occurred when attempting to delete the investigator!");
        }
        return "redirect:/investigators";
    }
}
