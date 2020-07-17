package com.pdclientmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.JudgeService;
import com.pdclientmanager.util.ControllerUtils;

@Controller
public class CaseController {
    
    private CaseService caseService;
    private AttorneyService attorneyService;
    private JudgeService judgeService;
    
    @Autowired
    public CaseController(CaseService caseService, AttorneyService attorneyService,
            JudgeService judgeService) {
        this.caseService = caseService;
        this.attorneyService = attorneyService;
        this.judgeService = judgeService;
    }
    
    @GetMapping("/cases")
    public String caseManagement(Model model) {
        return "cases/caseManagement";
    }
    
    @GetMapping("/cases/add")
    public String showNewCaseForm(Model model) {
        CaseForm caseForm = new CaseForm();
        List<AttorneyLightProjection> activeAttorneys = attorneyService.findAllActive();
        List<JudgeProjection> activeJudges = judgeService.findAllActive();
        
        model.addAttribute("caseForm", caseForm);
        model.addAttribute("activeAttorneys", activeAttorneys);
        model.addAttribute("activeJudges", activeJudges);
        
        return "cases/caseForm";
    }
    
    @PostMapping(path = "/cases")
    public String saveCase(@ModelAttribute("caseForm") @Valid CaseForm caseForm, 
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            List<AttorneyLightProjection> activeAttorneys = attorneyService.findAllActive();
            List<JudgeProjection> activeJudges = judgeService.findAllActive();
            
            model.addAttribute("caseForm", caseForm);
            model.addAttribute("activeAttorneys", activeAttorneys);
            model.addAttribute("activeJudges", activeJudges);
            
            return "cases/caseForm";
            
        } else {
            Long entityId = caseService.save(caseForm);
            
            redirectAttributes.addAttribute("css", "success");
            redirectAttributes.addAttribute("msg", "Case saved successfully!");
            
            return "redirect:/cases/" + entityId;
        }
    }
    
    @GetMapping("/cases/list")
    public String showAllCases(@PageableDefault(page = 0, sort = {"client.name", "dateClosed", "caseNumber"})
            Pageable pageRequest, Model model) {
        Page<CaseLightProjection> casePage = caseService.findAll(pageRequest, CaseLightProjection.class);
        
        model.addAttribute("cases", casePage.getContent());
        model.addAttribute("page", casePage.getNumber());
        model.addAttribute("totalPages", casePage.getTotalPages());
        model.addAttribute("size", casePage.getSize());
        
        return "cases/listCases";
    }
    
    @GetMapping("/cases/{id}")
    public String showCase(@PathVariable("id") Long id, Model model) {
        CaseProjection courtCase = caseService.findById(id, CaseProjection.class);
        
        model.addAttribute("courtCase", courtCase);
        
        return "cases/showCase";
    }
    
    @GetMapping("/cases/list/searchResults")
    public String showSearchedCases(@RequestParam("q") String searchTerm, 
            @PageableDefault(page = 0, sort = {"dateClosed", "client.name", "caseNumber"})
            Pageable pageRequest, HttpServletRequest request,
            Model model, final RedirectAttributes redirectAttributes) {
        if(searchTerm.length() <2) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Please enter a search term 3 or more characters");
            return ControllerUtils.getPreviousPage(request).orElse("/");
        }
        Page<CaseProjection> casePage = caseService.findAllWithClientName(pageRequest, searchTerm, CaseProjection.class);
        model.addAttribute("cases", casePage.getContent());
        model.addAttribute("page", casePage.getNumber());
        model.addAttribute("totalPages", casePage.getTotalPages());
        model.addAttribute("size", casePage.getSize());
        
        return "cases/listCases";
    }
    
    @GetMapping("/cases/{id}/update")
    public String showUpdateCaseForm(@PathVariable("id") Long id, Model model)
            throws JsonProcessingException {
        CaseForm caseForm = caseService.findFormById(id);
        List<AttorneyLightProjection> activeAttorneys = attorneyService.findAllActive();
        List<JudgeProjection> activeJudges = judgeService.findAllActive();
        
        model.addAttribute("caseForm", caseForm);
        model.addAttribute("activeAttorneys", activeAttorneys);
        model.addAttribute("activeJudges", activeJudges);
        
        return "cases/caseForm";
    }
    
    @GetMapping("/cases/reassignment")
    public String showReassignmentForm(Model model) {
        List<AttorneyLightProjection> activeAttorneys = attorneyService.findAllActive();
        
        model.addAttribute("activeAttorneys", activeAttorneys);
        
        return "cases/reassignment";
    }
    
    @PostMapping("/cases/reassignment")
    public String reassignCaseload(@RequestParam("prevAssignedAttorneyId") Long prevId,
            @RequestParam("newAssignedAttorneyId") Long newId,
            @RequestParam("reassignedCases") String reassignedCases,
            final RedirectAttributes redirectAttributes) {
        if(prevId == newId) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Must select different attorneys to reassign caseload");
            return "redirect:/cases/reassignment";
        }
        
        if(reassignedCases.equals("openCases")) {
            caseService.reassignOpenCases(prevId, newId);
        } else {
            caseService.reassignAllCases(prevId, newId);
        }
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Caseload reassigned successfully!");
        return "redirect:/attorneys/" + newId;
    }

    @PostMapping("/cases/{id}/delete")
    public String deleteCaseById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        caseService.deleteById(id);
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Case is deleted!");

        return "redirect:/cases";
    }
}