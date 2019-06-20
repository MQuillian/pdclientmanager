package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.Employee;

public interface EmployeeService<T extends Employee> extends CrudService<T> {

    public List<T> getAllActive();
    
}
