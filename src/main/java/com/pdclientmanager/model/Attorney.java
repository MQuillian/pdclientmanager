package com.pdclientmanager.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "attorneys")
public class Attorney extends Employee {   //Refactor - not DRY - Investigator would also be Employee
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "attorney_id")
//    private Long id;
//    
//    @Column(name = "name")
//    @NotEmpty(message = "Name")
//    private String name;
//    
//    @Column(name = "employment_status")
//    @NotNull(message = "Employment status")
//    private EmploymentStatus employmentStatus;

    public Attorney() {
        
    }

    public Attorney(Long id, String name, EmploymentStatus employmentStatus) {
        super();
    }

    @Override
    public String toString() {
        return "Attorney [id=" + super.getId() + ", name=" + super.getName() + ", employmentStatus=" + super.getEmploymentStatus() + "]";
    }
}