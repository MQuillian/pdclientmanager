package com.pdclientmanager.model.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.pdclientmanager.model.entity.CaseStatus;

public class CaseFormDto {

    private Long id;
    private String caseNumber;
    private CaseStatus caseStatus;
    private LocalDate dateOpened;
    private LocalDate dateClosed;
    private Long clientId;
    private Long judgeId;
    private Long attorneyId;
    private Map<Integer, Long> chargedCountsIds;
    
    public CaseFormDto() {
        
    }

    public CaseFormDto(Long id, String caseNumber, CaseStatus caseStatus, LocalDate dateOpened, LocalDate dateClosed,
            Long clientId, Long judgeId, Long attorneyId, Map<Integer, Long> chargedCountsIds) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.caseStatus = caseStatus;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.clientId = clientId;
        this.judgeId = judgeId;
        this.attorneyId = attorneyId;
        this.chargedCountsIds = chargedCountsIds;
    }
    
    public void addChargedCountId(Integer countNumber, Long id) {
        this.chargedCountsIds.put(countNumber,id);
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

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Map<Integer, Long> getChargedCountsIds() {
        return chargedCountsIds;
    }

    public void setChargedCountsIds(Map<Integer, Long> chargedCountsIds) {
        this.chargedCountsIds = chargedCountsIds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
        result = prime * result + ((caseStatus == null) ? 0 : caseStatus.hashCode());
        result = prime * result + ((dateClosed == null) ? 0 : dateClosed.hashCode());
        result = prime * result + ((dateOpened == null) ? 0 : dateOpened.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        if (caseStatus != other.caseStatus)
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
        private CaseStatus caseStatus = CaseStatus.OPEN;
        private LocalDate dateOpened = LocalDate.of(2000, 01, 01);
        private LocalDate dateClosed = null;
        private Long clientId = 1L;
        private Long judgeId = 1L;
        private Long attorneyId = 1L;
        private Map<Integer, Long> chargedCountsIds = new HashMap<>();
        
        public CaseFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public CaseFormDtoBuilder withCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
            return this;
        }
        
        public CaseFormDtoBuilder withCaseStatus(CaseStatus caseStatus) {
            this.caseStatus = caseStatus;
            return this;
        }
        
        public CaseFormDtoBuilder withDateOpened(LocalDate dateOpened) {
            this.dateOpened = dateOpened;
            return this;
        }
        
        public CaseFormDtoBuilder withDateClosed(LocalDate dateClosed) {
            this.dateClosed = dateClosed;
            return this;
        }
        
        public CaseFormDtoBuilder withClientId(Long clientId) {
            this.clientId = clientId;
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
        
        public CaseFormDtoBuilder withChargedCountsIds(Map<Integer,Long> chargedCountsIds) {
            this.chargedCountsIds = chargedCountsIds;
            return this;
        }
        
        public CaseFormDto build() {
            return new CaseFormDto(id, caseNumber, caseStatus, dateOpened, dateClosed,
                    clientId, judgeId, attorneyId, chargedCountsIds);
        }
    }
}
