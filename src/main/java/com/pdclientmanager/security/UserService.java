package com.pdclientmanager.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pdclientmanager.model.form.UserForm;

public interface UserService extends UserDetailsService{

    public Long saveUser(UserForm userForm);
    
    public UserForm findFormById(Long id);
    
    public List<User> findAll();
    
    public boolean deleteById(Long id);
}