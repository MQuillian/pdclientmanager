package com.pdclientmanager.controller;

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

import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.service.JudgeService;

@Controller
public class JudgeController {

    private JudgeService judgeService;
    
    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }
    
    @GetMapping("/judges")
    public String showJudgeManagement() {
        return "judges/judgeManagement";
    }
    
    @GetMapping("/judges/add")
    public String showNewJudgeForm(Model model) {
        model.addAttribute("judgeForm", new JudgeDto());
        
        return "judges/judgeForm";
    }
    
    @PostMapping("/judges")
    public String saveJudge(@ModelAttribute("judgeForm") @Valid JudgeDto judgeForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "judges/judgeForm";
        } else {
            Long entityId = judgeService.save(judgeForm);
            
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Judge saved successfully!");
            
            return "redirect:/judges/" + entityId;
        }
    }
    
    @GetMapping("/judges/list")
    public String showAllJudges(Model model) {
        model.addAttribute("judgeList", judgeService.findAll());
        
        return "judges/listJudges";
    }
    
    @GetMapping("/judges/{id}")
    public String showJudge(@PathVariable("id") Long targetId, Model model,
            final RedirectAttributes redirectAttributes) {
        JudgeDto judge = judgeService.findById(targetId);
        
        if(judge == null) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Judge could not be found");
            
            return "redirect:/judges/list";
        } else {
            model.addAttribute("judge", judge);
            
            return "judges/showJudge";
        }
    }
    
    @GetMapping("/judges/{id}/update")
    public String updateJudge(@PathVariable("id") Long targetId, Model model) {
        JudgeDto judgeForm = judgeService.findById(targetId);
        
        model.addAttribute("judgeForm", judgeForm);
            
        return "judges/judgeForm";
    }
    
    @PostMapping("/judges/{id}/delete")
    public String deleteJudge(@PathVariable("id") Long targetId,
            final RedirectAttributes redirectAttributes) {
        judgeService.deleteById(targetId);
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Judge deleted successfully!");
        
        return "redirect:/judges/list";
    }
}
