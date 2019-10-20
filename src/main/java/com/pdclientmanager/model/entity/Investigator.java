package com.pdclientmanager.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
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
        for(Attorney attorney : assignedAttorneys) {
            attorney.setInvestigator(this);
        }
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
    
    public static class InvestigatorBuilder {
        
        Long id = 1L;
        String name = "Default Investigator";
        EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        List<Attorney> assignedAttorneys = new ArrayList<>();
        
        public InvestigatorBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public InvestigatorBuilder withAssignedAttorneys(List<Attorney> assignedAttorneys) {
            this.assignedAttorneys = assignedAttorneys;
            return this;
        }
        
        public Investigator build() {
            return new Investigator(id, name, employmentStatus, assignedAttorneys);
        }
    }
}
