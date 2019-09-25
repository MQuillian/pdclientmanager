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
public class CaseController {
    
    private CaseService caseService;
    
    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }
    
//    @GetMapping("/cases/add")
//    public String showNewCaseForm(Model model) {
//        CaseFormDto courtCase = new CaseFormDto();
//        model.addAttribute("courtCase", courtCase);
//    }
//    
//    persistOrMergeCase
//    
//    @GetMapping("/cases")
//    public String showAllCases(Model model) {
//        model.addAttribute("cases", caseService.getAllWithInitializedClients());
//        return "cases/listCases";
//    }
//    
//    @GetMapping("/cases/{id}")
//    public String showCase(@PathVariable("id") Long id, Model model,
//            final RedirectAttributes redirectAttributes) {
//        CaseDto courtCase = caseService.getById(id);
//        if(courtCase == null) {
//            redirectAttributes.addAttribute("css", "danger");
//            redirectAttributes.addAttribute("msg", "Case could not be found...");
//            return "redirect:/cases";
//        }
//        model.addAttribute("case", courtCase);
//        return "cases/showCase";
//    }
//    
//    showUpdateCaseForm
//    
//    deleteCaseById
    
//    private AttorneyService attorneyService;
//    private InvestigatorService investigatorService;
//    private CaseService caseService;
//    
//    @Autowired
//    public EmployeeController(AttorneyService attorneyService,
//            InvestigatorService investigatorService,
//            CaseService caseService, AttorneyMapper attorneyMapper,
//            InvestigatorMapper investigatorMapper) {
//        this.attorneyService = attorneyService;
//        this.investigatorService = investigatorService;
//        this.caseService = caseService;
//    }
//    
//    @InitBinder 
//    public void initBinder(WebDataBinder b) {
//        b.registerCustomEditor(AttorneyDto.class,
//                new AttorneyDtoEditor(attorneyService));
//        b.registerCustomEditor(AttorneyMinimalDto.class,
//                new AttorneyMinimalDtoEditor(attorneyService));
//        b.registerCustomEditor(InvestigatorDto.class, 
//                new InvestigatorDtoEditor(investigatorService));
//        b.registerCustomEditor(InvestigatorMinimalDto.class,
//                new InvestigatorMinimalDtoEditor(investigatorService));
//    } 
//    
//    @GetMapping("/employeeManagement")
//    public String employeeManagement(Model model) {
//        model.addAttribute("activeAttorneys", attorneyService.getAllActive());
//        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
//        return "employeeManagement";
//    }
//    
//    @GetMapping("/attorneys/add")
//    public String showNewAttorneyForm(Model model) {
//        AttorneyFormDto attorney = new AttorneyFormDto();
//        model.addAttribute("attorneyForm", attorney);
//        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
//        return "attorneys/attorneyForm";
//    }
//    
//    @PostMapping("/attorneys")
//    public String persistOrMergeAttorney(
//            @ModelAttribute("attorneyForm") @Valid AttorneyFormDto attorneyForm,
//            BindingResult result, Model model,
//            final RedirectAttributes redirectAttributes) {
//        if(result.hasErrors()) {
//            return "attorneys/attorneyForm";
//        } else {
//            Long entityId;
//            if(attorneyForm.isNew()) {
//                entityId = attorneyService.persist(attorneyForm);
//                redirectAttributes.addFlashAttribute("css", "success");
//                redirectAttributes.addFlashAttribute("msg", "Attorney added successfully!");
//            } else {
//                entityId = attorneyService.merge(attorneyForm);
//                redirectAttributes.addFlashAttribute("css", "success");
//                redirectAttributes.addFlashAttribute("msg", "Attorney updated successfully!");
//            }
//            return "redirect:/attorneys/" + entityId;
//        }
//    }
//    
//    @GetMapping("/attorneys")
//    public String showAllAttorneys(Model model) {
//        model.addAttribute("attorneyList", attorneyService.getAll());
//        return "attorneys/listAttorneys";
//    }
//    
//    @GetMapping("/attorneys/{id}")
//    public String showAttorney(@PathVariable("id") Long id, Model model,
//            final RedirectAttributes redirectAttributes) {
//        AttorneyDto attorney = attorneyService.getById(id);
//        if(attorney == null) {
//            redirectAttributes.addFlashAttribute("css", "danger");
//            redirectAttributes.addFlashAttribute("msg", "Attorney could not be found...");
//            return "redirect:/attorneys";
//        }
//        List<CaseDto> caseload = caseService.getAllActiveByAttorneyId(id);
//        for(CaseDto courtCase : caseload) {
//            Hibernate.initialize(courtCase.getClient());
//        }
//        model.addAttribute("attorney", attorney);
//        model.addAttribute("caseload", caseload);
//        return "attorneys/showAttorney";
//    }
//    
//    @GetMapping("/attorneys/{id}/update")
//    public String showUpdateAttorneyForm(@PathVariable("id") Long targetId, Model model) {
//        AttorneyFormDto attorney = attorneyService.getFormById(targetId);
//        model.addAttribute("attorneyForm", attorney);
//        model.addAttribute("activeInvestigators", investigatorService.getAllActive());
//        return "attorneys/attorneyForm";
//    }
//    
//    @PostMapping("/attorneys/{id}/delete")
//    public String deleteAttorneyById(@PathVariable("id") Long id,
//            final RedirectAttributes redirectAttributes) {
//        if(attorneyService.deleteById(id)) {
//            redirectAttributes.addFlashAttribute("css", "success");
//            redirectAttributes.addFlashAttribute("msg", "Attorney is deleted!");
//        } else {
//            redirectAttributes.addFlashAttribute("css", "danger");
//            redirectAttributes.addFlashAttribute("msg", 
//                    "The attorney's active caseload must be reassigned before deleting!");
//        }
//        return "redirect:/attorneys";
//    }
}
