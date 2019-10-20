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

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.InvestigatorService;
import com.pdclientmanager.util.AttorneyDtoEditor;
import com.pdclientmanager.util.AttorneyMinimalDtoEditor;
import com.pdclientmanager.util.InvestigatorDtoEditor;
import com.pdclientmanager.util.InvestigatorMinimalDtoEditor;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Controller
public class EmployeeController {
    
    private AttorneyService attorneyService;
    private InvestigatorService investigatorService;
    private CaseService caseService;
    
    @Autowired
    public EmployeeController(AttorneyService attorneyService,
            InvestigatorService investigatorService,
            CaseService caseService, AttorneyMapper attorneyMapper,
            InvestigatorMapper investigatorMapper) {
        this.attorneyService = attorneyService;
        this.investigatorService = investigatorService;
        this.caseService = caseService;
    }
    
    @InitBinder 
    public void initBinder(WebDataBinder b) {
        b.registerCustomEditor(AttorneyDto.class,
                new AttorneyDtoEditor(attorneyService));
        b.registerCustomEditor(AttorneyMinimalDto.class,
                new AttorneyMinimalDtoEditor(attorneyService));
        b.registerCustomEditor(InvestigatorDto.class, 
                new InvestigatorDtoEditor(investigatorService));
        b.registerCustomEditor(InvestigatorMinimalDto.class,
                new InvestigatorMinimalDtoEditor(investigatorService));
    } 
    
    @GetMapping("/employeeManagement")
    public String employeeManagement(Model model) {
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "employeeManagement";
    }
    
    @GetMapping("/attorneys/add")
    public String showNewAttorneyForm(Model model) {
        AttorneyFormDto attorney = new AttorneyFormDto();
        model.addAttribute("attorneyForm", attorney);
        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
        return "attorneys/attorneyForm";
    }
    
    @PostMapping("/attorneys")
    public String persistOrMergeAttorney(
            @ModelAttribute("attorneyForm") @Valid AttorneyFormDto attorneyForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "attorneys/attorneyForm";
        } else {
            Long entityId;
            if(attorneyForm.isNew()) {
                entityId = attorneyService.persist(attorneyForm);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Attorney added successfully!");
            } else {
                entityId = attorneyService.merge(attorneyForm);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Attorney updated successfully!");
            }
            return "redirect:/attorneys/" + entityId;
        }
    }
    
    @GetMapping("/attorneys")
    public String showAllAttorneys(Model model) {
        model.addAttribute("attorneyList", attorneyService.getAll());
        return "attorneys/listAttorneys";
    }
    
    @GetMapping("/attorneys/{id}")
    public String showAttorney(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {
        AttorneyDto attorney = attorneyService.getById(id);
        if(attorney == null) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Attorney could not be found...");
            return "redirect:/attorneys";
        }
        List<CaseDto> caseload = caseService.getAllActiveByAttorneyId(id);
        for(CaseDto courtCase : caseload) {
            Hibernate.initialize(courtCase.getClient());
        }
        model.addAttribute("attorney", attorney);
        model.addAttribute("caseload", caseload);
        return "attorneys/showAttorney";
    }
    
    @GetMapping("/attorneys/{id}/update")
    public String showUpdateAttorneyForm(@PathVariable("id") Long targetId, Model model) {
        AttorneyFormDto attorney = attorneyService.getFormById(targetId);
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
                    "The attorney's active caseload must be reassigned before deleting!");
        }
        return "redirect:/attorneys";
    }
    
    @GetMapping("/investigators/add")
    public String showNewInvestigatorForm(Model model) {
        InvestigatorFormDto investigator = new InvestigatorFormDto();
        model.addAttribute("investigatorForm", investigator);
        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
        return "investigators/investigatorForm";
    }
    
    @PostMapping("/investigators")
    public String persistOrMergeInvestigator(
            @ModelAttribute("investigator") @Valid InvestigatorFormDto investigator, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "addInvestigatorForm";
        } else {
            Long entityId;
            if(investigator.isNew()) {
                entityId = investigatorService.persist(investigator);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Investigator added successfully!");
            } else {
                entityId = investigatorService.merge(investigator);
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Investigator updated successfully!");
            }
            return "redirect:/investigators/" + entityId;
        }
    }
    
    @GetMapping("/investigators")
    public String showAllInvestigators(Model model) {
        model.addAttribute("investigatorList", investigatorService.getAll());
        return "investigators/listInvestigators";
    }
    
    @GetMapping("/investigators/{id}")
    public String showInvestigator(@PathVariable("id") Long targetId, Model model) {
        InvestigatorDto investigator = investigatorService
                .getById(targetId);
        if(investigator == null) {
            model.addAttribute("css", "error");
            model.addAttribute("msg", "Investigator not found");
            return "redirect:/investigators";
        }
        Hibernate.initialize(investigator.getAssignedAttorneys());
        model.addAttribute("investigator", investigator);
        model.addAttribute("assignedAttorneys", investigator.getAssignedAttorneys());
        return "investigators/showInvestigator";
    }
    
    @GetMapping("/investigators/{id}/update")
    public String showUpdateInvestigatorForm(@PathVariable("id") Long targetId, Model model,
            final RedirectAttributes redirectAttributes) {
        InvestigatorDto investigator = 
                investigatorService.getById(targetId);
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
