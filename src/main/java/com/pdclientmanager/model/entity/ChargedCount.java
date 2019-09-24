package com.pdclientmanager.model.entity;

import javax.persistence.Entity;
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
    
    @ManyToOne
    @JoinColumn(name = "court_case", insertable = false, updatable = false)
    private Case courtCase;
    
    public ChargedCount() {
        
    }

    public ChargedCount(Long id, Integer countNumber, Charge charge, Case courtCase) {
        this.id = id;
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
        
        Long id = 1L;
        Integer countNumber = 1;
        Charge charge = new Charge();
        Case courtCase = new Case();
        
        public ChargedCountBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public ChargedCountBuilder withCountNumber(Integer countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        
        public ChargedCountBuilder withCharge(Charge charge) {
            this.charge = charge;
            return this;
        }
        
        public ChargedCountBuilder withCourtCase(Case courtCase) {
            this.courtCase = courtCase;
            return this;
        }
        
        public ChargedCount build() {
            return new ChargedCount(id, countNumber, charge, courtCase);
        }
    }
}
