package com.pdclientmanager.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

@Entity
@NamedEntityGraph(name = "Attorney.fullProjection",
        attributeNodes = {
                @NamedAttributeNode(value = "investigator"),
                @NamedAttributeNode(value = "caseload", subgraph = "Case.withClient")},
        subgraphs = {
                @NamedSubgraph(name = "Case.withClient",
                        attributeNodes = @NamedAttributeNode(value = "client"))
        })
public class Attorney extends Employee {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investigator")
    private Investigator investigator;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attorney")
    private List<Case> caseload;
    
    public Attorney() {
        
    }

    public Attorney(Long id, String name, WorkingStatus workingStatus, Investigator investigator, List<Case> caseload) {
        super(id, name, workingStatus);
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
        WorkingStatus workingStatus = WorkingStatus.ACTIVE;
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
        
        public AttorneyBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
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
            return new Attorney (id, name, workingStatus, investigator, caseload);
        }
    }
}