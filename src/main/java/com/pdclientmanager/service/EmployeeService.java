package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.Employee;

public interface EmployeeService<T extends Employee> extends CrudService<T> {
        
    public T getByIdWithInitializedAssignedAttorneys(Long targetId);
    
    public List<T> getAllActive();
}
