package com.pdclientmanager.service;

import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.UserForm;

public interface UserService {

    public String saveUser(UserForm userForm);
}
