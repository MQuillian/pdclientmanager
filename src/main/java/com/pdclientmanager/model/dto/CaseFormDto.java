package com.pdclientmanager.model.dto;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.util.validator.CaseNumberConstraint;
import com.pdclientmanager.util.validator.ConsistentDatesConstraint;
import com.pdclientmanager.util.validator.DateClosedConstraint;
import com.pdclientmanager.util.validator.DateOpenedConstraint;

@ConsistentDatesConstraint
public class CaseFormDto {

    private Long id;
    
    @CaseNumberConstraint
    private String caseNumber;
    
    @DateOpenedConstraint
    private String dateOpened;
    
    @DateClosedConstraint
    private String dateClosed;
    
    @NotNull
    private Long clientId;
    
    private String clientName;
    
    @NotNull
    private Long judgeId;
    
    @NotNull
    private Long attorneyId;
    
    @NotEmpty
    private SortedMap<Integer, Long> chargedCountsIds;
    
    private SortedMap<Integer, String> chargedCountsStrings;
    
    public CaseFormDto() {
        
    }

    public CaseFormDto(Long id, String caseNumber, String dateOpened, String dateClosed,
            Long clientId, String clientName, Long judgeId, Long attorneyId, SortedMap<Integer, Long> chargedCountsIds,
            SortedMap<Integer, String> chargedCountsStrings) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.clientId = clientId;
        this.clientName = clientName;
        this.judgeId = judgeId;
        this.attorneyId = attorneyId;
        this.chargedCountsIds = chargedCountsIds;
        this.chargedCountsStrings = chargedCountsStrings;
    }
    
    public void addChargedCount(Integer countNumber, Long id, String name) {
        this.chargedCountsIds.put(countNumber,id);
        this.chargedCountsStrings.put(countNumber, name);
    }
    
    public void addChargedCount(ChargedCount chargedCount) {
        this.chargedCountsIds.put(chargedCount.getId().getCountNumber(),
                chargedCount.getCharge().getId());
        this.chargedCountsStrings.put(chargedCount.getId().getCountNumber(),
                chargedCount.getCharge().toString());
    }
    
    public void removeChargedCount(Integer countNumber, Long id, String name) {
        this.chargedCountsIds.remove(countNumber, id);
        this.chargedCountsStrings.remove(countNumber, name);
    }
    
    public void removeChargedCount(ChargedCount chargedCount) {
        this.chargedCountsIds.remove(chargedCount.getId().getCountNumber(),
                chargedCount.getCharge().getId());
        this.chargedCountsStrings.remove(chargedCount.getId().getCountNumber(),
                chargedCount.getCharge().toString());
    }
    
    public boolean isNew() {
        return id == null;
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

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long judgeId) {
        this.judgeId = judgeId;
    }

    public Long getAttorneyId() {
        return attorneyId;
    }

    public void setAttorneyId(Long attorneyId) {
        this.attorneyId = attorneyId;
    }

    public SortedMap<Integer, Long> getChargedCountsIds() {
        return chargedCountsIds;
    }

    public void setChargedCountsIds(SortedMap<Integer, Long> chargedCountsIds) {
        this.chargedCountsIds = chargedCountsIds;
    }
    
    public SortedMap<Integer, String> getChargedCountsStrings() {
        return chargedCountsStrings;
    }
    
    public void setChargedCountsStrings(SortedMap<Integer, String> chargedCountsStrings) {
        this.chargedCountsStrings = chargedCountsStrings;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
        result = prime * result + ((dateClosed == null) ? 0 : dateClosed.hashCode());
        result = prime * result + ((dateOpened == null) ? 0 : dateOpened.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    

    @Override
    public String toString() {
        return "CaseFormDto [id=" + id + ", caseNumber=" + caseNumber + ", dateOpened=" + dateOpened + ", dateClosed="
                + dateClosed + ", clientId=" + clientId + ", judgeId=" + judgeId + ", attorneyId=" + attorneyId
                + ", chargedCountsIds=" + chargedCountsIds + "]" +", chargedCountsStrings=" + chargedCountsStrings + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CaseFormDto))
            return false;
        CaseFormDto other = (CaseFormDto) obj;
        if (caseNumber == null) {
            if (other.caseNumber != null)
                return false;
        } else if (!caseNumber.equals(other.caseNumber))
            return false;
        if (dateClosed == null) {
            if (other.dateClosed != null)
                return false;
        } else if (!dateClosed.equals(other.dateClosed))
            return false;
        if (dateOpened == null) {
            if (other.dateOpened != null)
                return false;
        } else if (!dateOpened.equals(other.dateOpened))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }



    public static class CaseFormDtoBuilder {
        
        private Long id = 1L;
        private String caseNumber = "00J0001";
        private String dateOpened = "01/01/2000";
        private String dateClosed = "";
        private Long clientId = 1L;
        private String clientName = "Client Name";
        private Long judgeId = 1L;
        private Long attorneyId = 1L;
        private SortedMap<Integer, Long> chargedCountsIds = new TreeMap<>();
        private SortedMap<Integer, String> chargedCountsStrings = new TreeMap<>();
        
        public CaseFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public CaseFormDtoBuilder withCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
            return this;
        }
        
        public CaseFormDtoBuilder withDateOpened(String dateOpened) {
            this.dateOpened = dateOpened;
            return this;
        }
        
        public CaseFormDtoBuilder withDateClosed(String dateClosed) {
            this.dateClosed = dateClosed;
            return this;
        }
        
        public CaseFormDtoBuilder withClientId(Long clientId) {
            this.clientId = clientId;
            return this;
        }
        
        public CaseFormDtoBuilder withClientName(String clientName) {
            this.clientName = clientName;
            return this;
        }
        
        public CaseFormDtoBuilder withJudgeId(Long judgeId) {
            this.judgeId = judgeId;
            return this;
        }
        
        public CaseFormDtoBuilder withAttorneyId(Long attorneyId) {
            this.attorneyId = attorneyId;
            return this;
        }
        
        public CaseFormDtoBuilder withChargedCountsIds(SortedMap<Integer,Long> chargedCountsIds) {
            this.chargedCountsIds = chargedCountsIds;
            return this;
        }
        
        public CaseFormDtoBuilder withChargedCountsStrings(SortedMap<Integer, String> chargedCountsStrings) {
            this.chargedCountsStrings = chargedCountsStrings;
            return this;
        }
        
        public CaseFormDto build() {
            return new CaseFormDto(id, caseNumber, dateOpened, dateClosed,
                    clientId, clientName, judgeId, attorneyId,
                    chargedCountsIds, chargedCountsStrings);
        }
    }
}
