package com.pdclientmanager.controller;

import javax.persistence.EntityNotFoundException;
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

import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.service.ChargeService;

@Controller
public class ChargeController {

private ChargeService chargeService;
    
    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }
    
    @GetMapping("/charges")
    public String showChargeManagement() {
        return "charges/chargeManagement";
    }
    
    @GetMapping("/charges/add")
    public String showNewChargeForm(Model model) {
        model.addAttribute("chargeForm", new ChargeForm());
        
        return "charges/chargeForm";
    }
    
    @PostMapping("/charges")
    public String saveCharge(@ModelAttribute("chargeForm") @Valid ChargeForm chargeForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "charges/chargeForm";
        } else {
            Long entityId = chargeService.save(chargeForm);
            
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Charge saved successfully!");
            
            return "redirect:/charges/" + entityId;
        }
    }
    
    @GetMapping("/charges/list")
    public String showAllCharges(Model model) {
        model.addAttribute("chargeList", chargeService.findAll());
        
        return "charges/listCharges";
    }
    
    @GetMapping("/charges/{id}")
    public String showCharge(@PathVariable("id") Long targetId, Model model,
            final RedirectAttributes redirectAttributes) {
        try {
            ChargeProjection charge = chargeService.findById(targetId, ChargeProjection.class);
            model.addAttribute("charge", charge);
            
            return "charges/showCharge";
        } catch(EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Charge could not be found");
            
            return "redirect:/charges/list";
        }
    }
    
    @GetMapping("/charges/{id}/update")
    public String updateCharge(@PathVariable("id") Long targetId, Model model) {
        ChargeForm chargeForm = chargeService.findFormById(targetId);
        
        model.addAttribute("chargeForm", chargeForm);
            
        return "charges/chargeForm";
    }
    
    @PostMapping("/charges/{id}/delete")
    public String deleteCharge(@PathVariable("id") Long targetId,
            final RedirectAttributes redirectAttributes) {
        chargeService.deleteById(targetId);
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Charge deleted successfully!");
        
        return "redirect:/charges/list";
    }
}
