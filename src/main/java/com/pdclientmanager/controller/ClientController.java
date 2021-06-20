package com.pdclientmanager.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
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
        ClientForm clientForm = new ClientForm();
        
        model.addAttribute("clientForm", clientForm);
        
        return "clients/clientForm";
    }
    
    @PostMapping("/clients")
    public String saveClient(@ModelAttribute("clientForm") @Valid ClientForm clientForm,
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
        model.addAttribute("clientList", clientService.findAllBy(ClientLightProjection.class));
        
        return "clients/listClients";
    }
    
    @GetMapping("/clients/{id}")
    public String showClient(@PathVariable("id") Long id, Model model,
            final RedirectAttributes redirectAttributes) {        
        try {
            ClientProjection client = clientService.findById(id, ClientProjection.class);
            model.addAttribute("client", client);
            
            return "clients/showClient";
        } catch(EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Client could not be found");
            
            return "redirect:/clients";
        }
    }
    
    @GetMapping("/clients/{id}/update")
    public String showUpdateClientForm(@PathVariable("id") Long targetId, Model model) {
        ClientForm clientForm = clientService.findFormById(targetId);
        
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
