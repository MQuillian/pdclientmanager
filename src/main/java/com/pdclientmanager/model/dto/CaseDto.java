package com.pdclientmanager.model.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CaseDto {

    private Long id;
    private String caseNumber;
    private LocalDate dateOpened;
    private LocalDate dateClosed;
    private ClientMinimalDto client;
    private JudgeDto judge;
    private AttorneyMinimalDto attorney;
    private Map<Integer, ChargedCountDto> chargedCounts;
    
    public CaseDto () {
        
    }

    public CaseDto(Long id, String caseNumber, LocalDate dateOpened, LocalDate dateClosed,
            ClientMinimalDto client, JudgeDto judge, AttorneyMinimalDto attorney,
            Map<Integer, ChargedCountDto> chargedCounts) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.client = client;
        this.judge = judge;
        this.attorney = attorney;
        this.chargedCounts = chargedCounts;
    }
    
    public void addChargedCount(Integer countNumber, ChargedCountDto chargedCount) {
        this.chargedCounts.put(countNumber, chargedCount);
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

    public ClientMinimalDto getClient() {
        return client;
    }

    public void setClient(ClientMinimalDto client) {
        this.client = client;
    }

    public JudgeDto getJudge() {
        return judge;
    }

    public void setJudge(JudgeDto judge) {
        this.judge = judge;
    }

    public AttorneyMinimalDto getAttorney() {
        return attorney;
    }

    public void setAttorney(AttorneyMinimalDto attorney) {
        this.attorney = attorney;
    }

    public Map<Integer, ChargedCountDto> getChargedCounts() {
        return chargedCounts;
    }

    public void setChargedCounts(Map<Integer, ChargedCountDto> chargedCounts) {
        this.chargedCounts = chargedCounts;
    }
    
    public void addChargedCount(ChargedCountDto chargedCount) {
        this.chargedCounts.put(chargedCount.getCountNumber(), chargedCount);
    }

    @Override
    public String toString() {
        return "CaseDto [id=" + id + ", caseNumber=" + caseNumber + ", dateOpened=" + dateOpened + ", dateClosed="
                + dateClosed + ", client=" + client + ", judge=" + judge + ", attorney=" + attorney + ", chargedCounts="
                + chargedCounts + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
        result = prime * result + ((chargedCounts == null) ? 0 : chargedCounts.hashCode());
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
        if (!(obj instanceof CaseDto))
            return false;
        CaseDto other = (CaseDto) obj;
        if (caseNumber == null) {
            if (other.caseNumber != null)
                return false;
        } else if (!caseNumber.equals(other.caseNumber))
            return false;
        if (chargedCounts == null) {
            if (other.chargedCounts != null)
                return false;
        } else if (!chargedCounts.equals(other.chargedCounts))
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

    public static class CaseDtoBuilder {
        
        private Long id = 1L;
        private String caseNumber = "00J000000";
        private LocalDate dateOpened = LocalDate.of(2000, 01, 01);
        private LocalDate dateClosed = null;
        private ClientMinimalDto client = new ClientMinimalDto.ClientMinimalDtoBuilder()
                .build();
        private JudgeDto judge = new JudgeDto.JudgeDtoBuilder()
                .build();
        private AttorneyMinimalDto attorney = new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
                .build();
        private Map<Integer, ChargedCountDto> chargedCounts = new HashMap<>();
        
        public CaseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public CaseDtoBuilder withCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
            return this;
        }
        
        public CaseDtoBuilder withDateOpened(LocalDate dateOpened) {
            this.dateOpened = dateOpened;
            return this;
        }
        
        public CaseDtoBuilder withDateClosed(LocalDate dateClosed) {
            this.dateClosed = dateClosed;
            return this;
        }
        
        public CaseDtoBuilder withClient(ClientMinimalDto client) {
            this.client = client;
            return this;
        }
        
        public CaseDtoBuilder withJudge(JudgeDto judge) {
            this.judge = judge;
            return this;
        }
        
        public CaseDtoBuilder withAttorney(AttorneyMinimalDto attorney) {
            this.attorney = attorney;
            return this;
        }
        
        public CaseDtoBuilder withChargedCounts(Map<Integer, ChargedCountDto> chargedCounts) {
            this.chargedCounts = chargedCounts;
            return this;
        }
        
        public CaseDto build() {
            return new CaseDto(id, caseNumber, dateOpened, dateClosed, client,
                    judge, attorney, chargedCounts);
        }
    }
}
