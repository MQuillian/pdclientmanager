package com.pdclientmanager.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties("assignedAttorneys")
public class Investigator extends Employee {

    @OneToMany(mappedBy = "investigator")
    private List<Attorney> assignedAttorneys;

    public Investigator() {
        
    }
    
    public Investigator(Long id, String name, EmploymentStatus employmentStatus, List<Attorney> assignedAttorneys) {
        super(id, name, employmentStatus);
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return result + prime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Investigator))
            return false;
        return true;
    }
}
