package com.pdclientmanager.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Investigator extends Employee {
    
    @OneToMany(mappedBy = "investigator")
    @NotEmpty(message = "Please select attorneys to assign")
    private List<Attorney> assignedAttorneys;

    public Investigator() {
        
    }
    
    public Investigator(Long id, String name, EmploymentStatus employmentStatus, List<Attorney> assignedAttorneys) {
        super();
        this.assignedAttorneys = assignedAttorneys;
    }
    
    
    
    public List<Attorney> getAssignedAttorneys() {
        return assignedAttorneys;
    }

    public void setAssignedAttorneys(List<Attorney> assignedAttorneys) {
        this.assignedAttorneys = assignedAttorneys;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
