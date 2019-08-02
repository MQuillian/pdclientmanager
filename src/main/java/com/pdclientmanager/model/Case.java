package com.pdclientmanager.model;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Case number")
    private String caseNumber;
    
    @NotEmpty(message = "Case status")
    private CaseStatus caseStatus;
    
    @NotEmpty(message = "Date opened")
    private LocalDate dateOpened;
    
    private LocalDate dateClosed;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = false)
    private Client client;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "judge", nullable = false)
    private Judge judge;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "attorney", nullable = false)
    private Attorney attorney;
    
    @OneToMany(mappedBy = "court_case")
    @MapKey(name = "count_number")
    private Map<Integer, Count> chargedCounts;
    
    public Case() {
        
    }

    public Case(Long id, String caseNumber, 
            CaseStatus caseStatus, LocalDate dateOpened, LocalDate dateClosed,
            Client client, Judge judge, Attorney attorney, Map<Integer, Count> chargedCounts) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.caseStatus = caseStatus;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.client = client;
        this.judge = judge;
        this.attorney = attorney;
        this.chargedCounts = chargedCounts;
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

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus status) {
        this.caseStatus = status;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDate dateClosed) {
        this.dateClosed = dateClosed;
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

    public Map<Integer, Count> getChargedCounts() {
        return chargedCounts;
    }

    public void setChargedCounts(Map<Integer, Count> chargedCounts) {
        this.chargedCounts = chargedCounts;
    }

    @Override
    public String toString() {
        return "Case [id=" + id + ", caseNumber=" + caseNumber + ", client=" + client + ", judge=" + judge
                + ", attorney=" + attorney + ", chargedCounts=" + chargedCounts + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Case))
            return false;
        Case other = (Case) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
