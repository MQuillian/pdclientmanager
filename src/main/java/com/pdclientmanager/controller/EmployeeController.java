package com.pdclientmanager.controller;

import javax.validation.Valid;

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
import com.pdclientmanager.model.Investigator;
import com.pdclientmanager.model.InvestigatorEditor;
import com.pdclientmanager.service.EmployeeService;

@Controller
public class EmployeeController {
    
    @Autowired
    EmployeeService<Attorney> attorneyService;
    
    @Autowired
    EmployeeService<Investigator> investigatorService;
    
    @InitBinder 
    public void initBinder(WebDataBinder b) {
        b.registerCustomEditor(Attorney.class, new AttorneyEditor(attorneyService));
        b.registerCustomEditor(Investigator.class, new InvestigatorEditor(investigatorService));
    } 
    
    @GetMapping("/employeeManagement")
    public String employeeManagement(Model model) {
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "employeeManagement";
    }
    
    @GetMapping("/attorneys/add")
    public String attorneyForm(Model model) {
        Attorney attorney = new Attorney();
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys")
    public String saveOrUpdateAttorney(@ModelAttribute("attorneyForm") @Valid Attorney attorney, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "attorneys/attorneyForm";
        } else {
            attorneyService.create(attorney);
            redirectAttributes.addFlashAttribute("css", "success");
            if(attorney.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "Attorney added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Attorney updated successfully!");
            }
            return "redirect:/attorneys/" + attorney.getId();
        }
    }
    
    @GetMapping("/attorneys")
    public String viewAllAttorneys(Model model) {
        model.addAttribute("attorneys", attorneyService.getAll());
        return "attorneys/list";
    }
    
    @GetMapping("/attorneys/{id}")
    public String showAttorney(@PathVariable("id") Long id, Model model) {
        System.out.println("Calling atty service for atty...");
        Attorney attorney = attorneyService.getById(id);
        System.out.println(attorney);
        if(attorney == null) {
            model.addAttribute("css", "error");
            model.addAttribute("msg", "Attorney not found");
        }
        model.addAttribute("attorney", attorney);
        return "attorneys/show";
    }
    
    @GetMapping("/attorneys/{id}/update")
    public String showUpdateAttorneyForm(@PathVariable("id") Long targetId, Model model) {
        Attorney attorney = attorneyService.getById(targetId);
        model.addAttribute("attorneyForm", attorney);
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys/{id}/delete")
    public String deleteAttorneyById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
//        if() {
            attorneyService.deleteById(id);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User is deleted!");
            return "redirect:/attorneys";
//        } else {
//        redirectAttributes.addFlashAttribute("css", "danger");
//        redirectAttributes.addFlashAttribute("msg", "Attorney has assigned cases! Please re-assign all cases and try again.");
//        return "redirect:/attorneys";
//        }
    }
    
    @GetMapping("/addInvestigatorForm")
    public String addInvestigatorForm(Model model) {
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        return "addInvestigatorForm";
    }
    
    @PostMapping("/addInvestigator")
    public String saveInvestigator(@ModelAttribute("investigator") @Valid Investigator investigator, 
            BindingResult result, Model model) {
        if(result.hasErrors()) {
        return "addInvestigatorForm";
        }
        investigatorService.create(investigator);
        for(Attorney attorney : investigator.getAssignedAttorneys()) {
            attorney.setInvestigator(investigator);
            attorneyService.update(attorney);
        }
        return "redirect:/employeeManagement";
    }
    
    @GetMapping("/manageInvestigator/{id}")
    public String manageInvestigator(@PathVariable("id") Long targetId, Model model) {
        model.addAttribute("investigator", investigatorService.getById(targetId));
        return "manageInvestigator";
    }

}
