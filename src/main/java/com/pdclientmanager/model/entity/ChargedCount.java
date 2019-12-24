package com.pdclientmanager.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChargedCount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer countNumber;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "charge")
    private Charge charge;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "court_case")
    private Case courtCase;
    
    public ChargedCount() {
        
    }

    public ChargedCount(Integer countNumber, Charge charge, Case courtCase) {
        this.countNumber = countNumber;
        this.charge = charge;
        this.courtCase = courtCase;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
    
    public Case getCourtCase() {
        return courtCase;
    }
    
    public void setCourtCase(Case courtCase) {
        this.courtCase = courtCase;
    }
    
    public static class ChargedCountBuilder {
        
        Integer countNumber = 1;
        Charge charge = new Charge();
        Case courtCase = new Case();
        
        public ChargedCountBuilder withCountNumber(Integer countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        
        public ChargedCountBuilder withCharge(Charge charge) {
            this.charge = charge;
            return this;
        }
        
        public ChargedCountBuilder withCase(Case courtCase) {
            this.courtCase = courtCase;
            return this;
        }
        
        public ChargedCount build() {
            return new ChargedCount(countNumber, charge, courtCase);
        }
    }
}
