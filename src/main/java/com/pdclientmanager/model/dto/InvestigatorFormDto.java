package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class InvestigatorFormDto {

    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    private List<Long> assignedAttorneyIds;
    
    public InvestigatorFormDto() {
        
    }

    public InvestigatorFormDto(Long id, String name, EmploymentStatus employmentStatus,
            List<Long> assignedAttorneyIds) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
        this.assignedAttorneyIds = assignedAttorneyIds;
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

    public List<Long> getAssignedAttorneyIds() {
        return assignedAttorneyIds;
    }

    public void setAssignedAttorneyIds(List<Long> assignedAttorneyIds) {
        this.assignedAttorneyIds = assignedAttorneyIds;
    }
    
    public static class InvestigatorFormDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default InvestigatorFormDto";
        private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        private List<Long> assignedAttorneyIds = new ArrayList<>();
        
        public InvestigatorFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withAssignedAttorneyIds(List<Long> assignedAttorneyIds) {
            this.assignedAttorneyIds = assignedAttorneyIds;
            return this;
        }
        
        public InvestigatorFormDto build() {
            return new InvestigatorFormDto(id, name, employmentStatus, assignedAttorneyIds);
        }
    }
}
