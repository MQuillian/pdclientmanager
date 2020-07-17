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

import com.pdclientmanager.model.form.UserForm;
import com.pdclientmanager.service.UserService;

@Controller
public class AdministrationController {
    
    private UserService userService;
    
    @Autowired
    public AdministrationController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/admin")
    public String showAdministrationHome() {
        return "/administration/systemManagement";
    }

    @GetMapping("/admin/addUser")
    public String showNewUserForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        List<String> availableRoles = new ArrayList<>();
        availableRoles.add("ADMIN");
        availableRoles.add("USER");
        model.addAttribute("availableRoles", availableRoles);
        return "/administration/userForm";
    }
    
    @PostMapping("/admin/users")
    public String saveUser(@ModelAttribute("userForm") @Valid UserForm userForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("userForm", userForm);
            List<String> availableRoles = new ArrayList<>();
            availableRoles.add("ADMIN");
            availableRoles.add("USER");
            model.addAttribute("availableRoles", availableRoles);
            return "/administration/userForm";
        } else {
            String username = userService.saveUser(userForm);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User saved successfully!");

            return "redirect:/admin";
        }
    }
}
