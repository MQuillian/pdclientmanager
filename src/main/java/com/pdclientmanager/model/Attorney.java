package com.pdclientmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "attorneys")
public class Attorney {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attorney_id")
    private Long id;
    
    @Column(name = "name")
    @NotEmpty(message = "Name")
    private String name;
    
    @Column(name = "employment_status")
    @NotNull(message = "Employment status")
    private EmploymentStatus employmentStatus;

    public Attorney() {
        
    }

    public Attorney(Long id, String name, EmploymentStatus employmentStatus) {
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
        return "Attorney [id=" + id + ", name=" + name + ", employmentStatus=" + employmentStatus + "]";
    }
    
    
    
    
}