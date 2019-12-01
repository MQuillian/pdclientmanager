package com.pdclientmanager.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;
import com.pdclientmanager.model.dto.ChargedCountMinimalDto;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.ChargeService;

// First figure out how to list for one, and THEN add rows

@Controller
public class CaseController {
    
    private CaseService caseService;
    private AttorneyService attorneyService;
    private ChargeService chargeService;
    
    @Autowired
    public CaseController(CaseService caseService, AttorneyService attorneyService,
            ChargeService chargeService) {
        this.caseService = caseService;
        this.attorneyService = attorneyService;
        this.chargeService = chargeService;
    }
    
    
    @GetMapping("/cases/add")
    public String showNewCaseForm(Model model) {
        CaseFormDto caseForm = new CaseFormDto();
//        List<AttorneyDto> activeAttorneys = attorneyService.findAllActive();
        model.addAttribute("caseForm", caseForm);
        List<ChargedCountMinimalDto> chargedCounts = new ArrayList<>();
        model.addAttribute("chargedCountsList", chargedCounts);
//        model.addAttribute("activeAttorneys", activeAttorneys);
        return "cases/caseForm";
    }

    @PostMapping(path="/cases")
    public String saveCase(
            @ModelAttribute("caseForm") @Valid CaseFormDto caseForm, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "cases/caseForm";
        } else {
            System.out.println("You've successfully hit the save point");
            if(caseForm.getChargedCountsIds() != null) {
                System.out.println(caseForm.getChargedCountsIds().size());
            }
            return "cases/listCases";
            
//            Long entityId = attorneyService.save(attorneyForm);
//            redirectAttributes.addFlashAttribute("css", "success");
//            redirectAttributes.addFlashAttribute("msg", "Attorney saved successfully!");
//            return "redirect:/attorneys/" + entityId;
        }
    }
    
    @GetMapping("/cases")
    public String showAllCases(Model model) {
        model.addAttribute("openCases", caseService.findAllOpen());
        return "cases/listCases";
    }
    
    @GetMapping("/cases/{id}")
    public String showCase(@PathVariable("id") Long id, Model model) {
        CaseDto courtCase = caseService.findById(id);
        model.addAttribute("courtCase", courtCase);
        return "cases/showCase";
    }
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
