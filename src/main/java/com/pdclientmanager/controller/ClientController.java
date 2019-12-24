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

import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.service.ClientService;

@Controller
public class ClientController {
    
    private ClientService clientService;
    
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @GetMapping("/clients")
    public String clientManagement(Model model) {
        return "clients/clientManagement";
    }
    
    @GetMapping("clients/add")
    public String showNewClientForm(Model model) {
        ClientMinimalDto clientForm = new ClientMinimalDto();
        
        model.addAttribute("clientForm", clientForm);
        
        return "clients/clientForm";
    }
    
    @PostMapping("/clients")
    public String saveClient(@ModelAttribute("clientForm") @Valid ClientMinimalDto clientForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "clients/clientForm";
        } else {
            Long entityId = clientService.save(clientForm);
            
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Client saved successfully!");
            
            return "redirect:/clients/" + entityId;
        }
    }
    
    @GetMapping("/clients/list")
    public String showAllClients(Model model) {
        model.addAttribute("clientList", clientService.findAll());
        
        return "clients/listClients";
    }
    
    @GetMapping("/clients/{id}")
    public String showClient(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {
        ClientDto client = clientService.findById(id);
        
        if(client == null) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Client could not be found");
            
            return "redirect:/clients";
        } else {
            model.addAttribute("client", client);
            
            return "clients/showClient";
        }
    }
    
    @GetMapping("/clients/{id}/update")
    public String showUpdateClientForm(@PathVariable("id") Long targetId, Model model) {
        ClientMinimalDto clientForm = clientService.findFormById(targetId);
        
        model.addAttribute("clientForm", clientForm);
        
        return "clients/clientForm";
    }
    
    @PostMapping("/clients/{id}/delete")
    public String deleteClientById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        clientService.deleteById(id);
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Client is deleted!");
        
        return "redirect:/clients";
    }
}
