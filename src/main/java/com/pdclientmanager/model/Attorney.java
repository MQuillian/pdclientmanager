package com.pdclientmanager.model;

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

    public Attorney(Long id, String name, EmploymentStatus employmentStatus, Investigator investigator) {
        super();
        this.investigator = investigator;
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
}