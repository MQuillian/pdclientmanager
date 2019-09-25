package com.pdclientmanager.model.entity;

import java.time.LocalDate;
import java.util.HashMap;
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
    
    @OneToMany(mappedBy = "courtCase")
    @MapKey(name = "countNumber")
    private Map<Integer, ChargedCount> chargedCounts;
    
    public Case() {
        
    }

    public Case(Long id, String caseNumber, 
            CaseStatus caseStatus, LocalDate dateOpened, LocalDate dateClosed,
            Client client, Judge judge, Attorney attorney, Map<Integer, ChargedCount> chargedCounts) {
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
    
    public void addChargedCount(ChargedCount chargedCount) {
        this.chargedCounts.put(chargedCount.getCountNumber(), chargedCount);
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

    public Map<Integer, ChargedCount> getChargedCounts() {
        return chargedCounts;
    }

    public void setChargedCounts(Map<Integer, ChargedCount> chargedCounts) {
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
    
    public static class CaseBuilder {
        
        Long id = 1L;
        String caseNumber = "00J000000";
        CaseStatus caseStatus = CaseStatus.OPEN;
        LocalDate dateOpened = LocalDate.of(2000, 1, 1);
        LocalDate dateClosed = null;
        Client client = new Client();
        Judge judge = new Judge();
        Attorney attorney = new Attorney();
        Map<Integer, ChargedCount> chargedCounts = new HashMap<>();
        
        public CaseBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public CaseBuilder withCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
            return this;
        }
        
        public CaseBuilder withCaseStatus(CaseStatus caseStatus) {
            this.caseStatus = caseStatus;
            return this;
        }
        
        public CaseBuilder withDateOpened(LocalDate dateOpened) {
            this.dateOpened = dateOpened;
            return this;
        }
        
        public CaseBuilder withDateClosed(LocalDate dateClosed) {
            this.dateClosed = dateClosed;
            return this;
        }
        
        public CaseBuilder withClient(Client client) {
            this.client = client;
            return this;
        }
        
        public CaseBuilder withJudge(Judge judge) {
            this.judge = judge;
            return this;
        }
        
        public CaseBuilder withAttorney(Attorney attorney) {
            this.attorney = attorney;
            return this;
        }
        
        public CaseBuilder withChargedCounts(Map<Integer, ChargedCount> chargedCounts) {
            this.chargedCounts = chargedCounts;
            return this;
        }
        
        public Case build() {
            return new Case(id, caseNumber, caseStatus, dateOpened, dateClosed,
                    client, judge, attorney, chargedCounts);
        }
    }
}
