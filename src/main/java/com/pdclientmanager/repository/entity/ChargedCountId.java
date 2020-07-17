package com.pdclientmanager.repository.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ChargedCountId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer countNumber;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "court_case")
    private Case courtCase;
    
    public ChargedCountId() {
        
    }
    
    public ChargedCountId(Integer countNumber, Case courtCase) {
        this.countNumber = countNumber;
        this.courtCase = courtCase;
    }
    
    public Integer getCountNumber() {
        return countNumber;
    }
    
    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }
    
    public Case getCourtCase() {
        return courtCase;
    }
    
    public void setCourtCase(Case courtCase) {
        this.courtCase = courtCase;
    }

    @Override
    public String toString() {
        return "ChargedCountId [countNumber=" + countNumber + ", courtCase=" + courtCase + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countNumber == null) ? 0 : countNumber.hashCode());
        result = prime * result + ((courtCase == null) ? 0 : courtCase.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ChargedCountId))
            return false;
        ChargedCountId other = (ChargedCountId) obj;
        if (countNumber == null) {
            if (other.countNumber != null)
                return false;
        } else if (!countNumber.equals(other.countNumber))
            return false;
        if (courtCase == null) {
            if (other.courtCase != null)
                return false;
        } else if (!courtCase.equals(other.courtCase))
            return false;
        return true;
    }
    
    
}
