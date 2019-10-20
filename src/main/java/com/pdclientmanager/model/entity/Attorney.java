package com.pdclientmanager.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Attorney extends Employee {

    @ManyToOne
    @JoinColumn(name = "investigator")
    private Investigator investigator;
    
    @OneToMany(mappedBy = "attorney")
    private List<Case> caseload;
    
    public Attorney() {
        
    }

    public Attorney(Long id, String name, EmploymentStatus employmentStatus, Investigator investigator, List<Case> caseload) {
        super(id, name, employmentStatus);
        this.investigator = investigator;
        this.caseload = caseload;
    }
    
    public Investigator getInvestigator() {
        return investigator;
    }

    public void setInvestigator(Investigator investigator) {
        this.investigator = investigator;
    }

    public List<Case> getCaseload() {
        return caseload;
    }

    public void setCaseload(List<Case> caseload) {
        this.caseload = caseload;
    }

    @Override
    public String toString() {
        return super.getName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((investigator == null) ? 0 : investigator.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Attorney))
            return false;
        Attorney other = (Attorney) obj;
        if (investigator == null) {
            if (other.investigator != null)
                return false;
        } else if (!investigator.equals(other.investigator))
            return false;
        return true;
    }
    
    public static class AttorneyBuilder {
        
        Long id = 1L;
        String name = "Default Attorney";
        EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        Investigator investigator = new Investigator();
        List<Case> caseload = new ArrayList<>();
        
        public AttorneyBuilder() {}
        
        public AttorneyBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public AttorneyBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public AttorneyBuilder withInvestigator(Investigator investigator) {
            this.investigator = investigator;
            return this;
        }
        
        public AttorneyBuilder withCaseload(List<Case> caseload) {
            this.caseload = caseload;
            return this;
        }
        
        public Attorney build() {
            return new Attorney (id, name, employmentStatus, investigator, caseload);
        }
    }
}