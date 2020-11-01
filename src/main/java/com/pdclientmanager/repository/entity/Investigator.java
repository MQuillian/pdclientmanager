package com.pdclientmanager.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

@Entity
@NamedEntityGraph(name = "Investigator.fullProjection",
        attributeNodes = {
                @NamedAttributeNode(value = "assignedAttorneys")
        })
public class Investigator extends Employee {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "investigator", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Attorney> assignedAttorneys;

    public Investigator() {
        
    }
    
    public Investigator(Long id, String name, WorkingStatus workingStatus, List<Attorney> assignedAttorneys) {
        super(id, name, workingStatus);
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
        WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        List<Attorney> assignedAttorneys = new ArrayList<>();
        
        public InvestigatorBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorBuilder withEmploymentStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public InvestigatorBuilder withAssignedAttorneys(List<Attorney> assignedAttorneys) {
            this.assignedAttorneys = assignedAttorneys;
            return this;
        }
        
        public Investigator build() {
            return new Investigator(id, name, workingStatus, assignedAttorneys);
        }
    }
}
