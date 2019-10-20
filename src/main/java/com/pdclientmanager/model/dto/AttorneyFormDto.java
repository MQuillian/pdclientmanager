package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class AttorneyFormDto {

    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    private Long investigatorId;
    
    public AttorneyFormDto() {
        
    }

    public AttorneyFormDto(Long id, String name, EmploymentStatus employmentStatus, Long investigatorId) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
        this.investigatorId = investigatorId;
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

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Long getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(Long investigatorId) {
        this.investigatorId = investigatorId;
    }
    
    public static class AttorneyFormDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default AttorneyFormDto";
        private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        private Long investigatorId = 1L;
        
        public AttorneyFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public AttorneyFormDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyFormDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public AttorneyFormDtoBuilder withInvestigatorId(Long investigatorId) {
            this.investigatorId = investigatorId;
            return this;
        }
        
        public AttorneyFormDto build() {
            return new AttorneyFormDto(id, name, employmentStatus, investigatorId);
        }
    }
}
