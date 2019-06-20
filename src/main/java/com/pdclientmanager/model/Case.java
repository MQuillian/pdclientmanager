package com.pdclientmanager.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Case number")
    private String caseNumber;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = false)
    private Client client;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "judge", nullable = false)
    private Judge judge;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "attorney", nullable = false)
    private Attorney attorney;
    
    @ManyToMany
    @JoinTable(name = "charged_counts",
            joinColumns = {@JoinColumn(name = "case")},
            inverseJoinColumns = {@JoinColumn(name = "charge")})
    private Set<Charge> chargedCounts;
    
    public Case() {
        
    }

    public Case(Long id, String caseNumber) {
        this.id = id;
        this.caseNumber = caseNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Judge getJudge() {
        return judge;
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public Attorney getAttorney() {
        return attorney;
    }

    public void setAttorney(Attorney attorney) {
        this.attorney = attorney;
    }

    public Set<Charge> getChargedCounts() {
        return chargedCounts;
    }

    public void setChargedCounts(Set<Charge> chargedCounts) {
        this.chargedCounts = chargedCounts;
    }

    @Override
    public String toString() {
        return "Case [id=" + id + ", caseNumber=" + caseNumber + ", client=" + client + ", judge=" + judge
                + ", attorney=" + attorney + ", chargedCounts=" + chargedCounts + "]";
    }
}
