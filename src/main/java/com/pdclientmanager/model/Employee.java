package com.pdclientmanager.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public abstract class Employee {  //Refactor for inheritance w/ Attorney and Investigator
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String name;
    
    @NotNull(message = "Employment status")
    private EmploymentStatus employmentStatus;
    
    public Employee() {
        
    }

    public Employee(Long id, String name, EmploymentStatus employmentStatus) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Enumerated(EnumType.ORDINAL)
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus(EmploymentStatus status) {
        this.employmentStatus = status;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", employmentStatus=" + employmentStatus + "]";
    }
}
