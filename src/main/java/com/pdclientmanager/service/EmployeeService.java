package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.entity.Employee;

public interface EmployeeService<T, S extends Employee> extends CrudService<T, S> {
        
    //Classes of type 'T' must be the corresponding DTO for the Employee type
    
    public List<T> getAllActive();
}
