package com.pdclientmanager.controller;

import java.io.IOException;
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
import com.pdclientmanager.model.projection.OfficeStatsDto;
import com.pdclientmanager.repository.AdministratorDao;
import com.pdclientmanager.security.UserService;

@Controller
public class AdministrationController {
    
    private UserService userService;
    private AdministratorDao adminDao;
    
    @Autowired
    public AdministrationController(UserService userService, AdministratorDao adminDao) {
        this.userService = userService;
        this.adminDao = adminDao;
    }
    
    @GetMapping("/admin")
    public String showAdministrationHome() {
        return "administration/systemManagement";
    }

    @GetMapping("/admin/users/addUser")
    public String showNewUserForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        model.addAttribute("availableRoles", listAllAvailableRoles());
        return "administration/userForm";
    }
    
    @PostMapping("/admin/users")
    public String saveUser(@ModelAttribute("userForm") @Valid UserForm userForm,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("availableRoles", listAllAvailableRoles());
            return "administration/userForm";
        } else {
            try {
            Long userId = userService.saveUser(userForm);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User saved successfully!");
            } catch(IOException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error saving user - contact system admin for assistance");
            }

            return "redirect:/admin";
        }
    }
    
    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "administration/listUsers";
    }
    
    @GetMapping("/admin/users/{id}/update")
    public String showUpdateUserForm(@PathVariable("id") Long targetId, Model model) {
        UserForm user = userService.findFormById(targetId);
        model.addAttribute("userForm", user);
        model.addAttribute("availableRoles", listAllAvailableRoles());
        return "administration/userForm";
    }
    
    @PostMapping("/admin/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") Long id,
            final RedirectAttributes redirectAttributes) {
        if(userService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", 
                    "User could not be found!");
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/admin/officeStats")
    public String getOfficeStats(Model model) {
        OfficeStatsDto officeStats = adminDao.getOfficeStats();
        model.addAttribute("officeStats", officeStats);
        
        return "administration/officeStats";
    }
    
    private List<String> listAllAvailableRoles() {
        List<String> availableRoles = new ArrayList<>();
        availableRoles.add("ADMIN");
        availableRoles.add("ATTORNEY");
        availableRoles.add("INVESTIGATOR");
        return availableRoles;
        
    }
}
