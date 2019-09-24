package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class AttorneyDto {

    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    private InvestigatorMinimalDto investigator;
    private List<CaseMinimalDto> caseload;
    
    public AttorneyDto() {
        
    }

    public AttorneyDto(Long id, String name, EmploymentStatus employmentStatus,
            InvestigatorMinimalDto investigator, List<CaseMinimalDto> caseload) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
        this.investigator = investigator;
        this.caseload = caseload;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus (EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public InvestigatorMinimalDto getInvestigator() {
        return investigator;
    }

    public void setInvestigator(InvestigatorMinimalDto investigator) {
        this.investigator = investigator;
    }
    
    public List<CaseMinimalDto> getCaseload() {
        return caseload;
    }
    
    public void setCaseload(List<CaseMinimalDto> caseload) {
        this.caseload = caseload;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((employmentStatus == null) ? 0 : employmentStatus.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof AttorneyDto))
            return false;
        AttorneyDto other = (AttorneyDto) obj;
        if (employmentStatus != other.employmentStatus)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    public static class AttorneyDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default AttorneyDto";
        private EmploymentStatus employmentStatus= EmploymentStatus.ACTIVE;
        private InvestigatorMinimalDto investigator = new InvestigatorMinimalDto
                .InvestigatorMinimalDtoBuilder()
                .build();
        private List<CaseMinimalDto> caseload = new ArrayList<>();
        
        public AttorneyDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public AttorneyDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public AttorneyDtoBuilder withInvestigator(InvestigatorMinimalDto investigator) {
            this.investigator = investigator;
            return this;
        }
        
        public AttorneyDtoBuilder withCaseload(List<CaseMinimalDto> caseload) {
            this.caseload = caseload;
            return this;
        }
        
        public AttorneyDto build() {
            return new AttorneyDto(id, name, employmentStatus, investigator, caseload);
        }
    }
}
