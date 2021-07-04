package com.pdclientmanager.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pdclientmanager.model.form.UserForm;
import com.pdclientmanager.repository.entity.User;

public interface UserService extends UserDetailsService{

    public Long saveUser(UserForm userForm) throws IOException;
    
    public UserForm findFormById(Long id);
    
    public UserForm findFormByFullName(String fullName);
    
    public List<User> findAll();
    
    public boolean deleteById(Long id);
    
    public UserForm getCurrentUserAsForm();
    
    public List<String> getCurrentUserRoles();
    
    public String getCurrentUserFullName();
}