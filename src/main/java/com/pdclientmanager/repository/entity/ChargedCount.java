package com.pdclientmanager.repository.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChargedCount {
    
    @EmbeddedId
    private ChargedCountId id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "charge")
    private Charge charge;
    
    public ChargedCount() {
        
    }

    public ChargedCount(ChargedCountId id, Charge charge) {
        this.id = id;
        this.charge = charge;
    }
    
    public ChargedCount(Integer countNumber, Charge charge) {
        this.id = new ChargedCountId(countNumber, null);
        this.charge = charge;
    }
    
    public ChargedCount(Integer countNumber, Case courtCase, Charge charge) {
        this.id = new ChargedCountId(countNumber, courtCase);
        this.charge = charge;
    }
    
    public ChargedCountId getId() {
        return id;
    }
    
    public void setId(ChargedCountId id) {
        this.id = id;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
    
    // Getter for use in Spring Data projection interface
    public Integer getCountNumber() {
        return id.getCountNumber();
    }

    @Override
    public String toString() {
        return "ChargedCount [id=" + id + ", charge=" + charge + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((charge == null) ? 0 : charge.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ChargedCount))
            return false;
        ChargedCount other = (ChargedCount) obj;
        if (charge == null) {
            if (other.charge != null)
                return false;
        } else if (!charge.equals(other.charge))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public static class ChargedCountBuilder {

        ChargedCountId id = new ChargedCountId(1, new Case());
        Charge charge = new Charge();
        
        public ChargedCountBuilder withId(ChargedCountId id) {
            this.id = id;
            return this;
        }
        
        public ChargedCountBuilder withCharge(Charge charge) {
            this.charge = charge;
            return this;
        }
        
        public ChargedCount build() {
            return new ChargedCount(id, charge);
        }
    }
}
