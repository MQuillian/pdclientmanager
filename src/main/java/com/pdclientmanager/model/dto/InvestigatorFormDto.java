package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.WorkingStatus;

public class InvestigatorFormDto {

    private Long id;
    private String name;
    private WorkingStatus workingStatus;
    private List<Long> assignedAttorneyIds;
    
    public InvestigatorFormDto() {
        
    }

    public InvestigatorFormDto(Long id, String name, WorkingStatus workingStatus,
            List<Long> assignedAttorneys) {
        this.id = id;
        this.name = name;
        this.workingStatus = workingStatus;
        this.assignedAttorneyIds = assignedAttorneys;
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

    public WorkingStatus getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(WorkingStatus workingStatus) {
        this.workingStatus = workingStatus;
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
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        private List<Long> assignedAttorneys = new ArrayList<>();
        
        public InvestigatorFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public InvestigatorFormDtoBuilder withAssignedAttorneys(List<Long> assignedAttorneys) {
            this.assignedAttorneys = assignedAttorneys;
            return this;
        }
        
        public InvestigatorFormDto build() {
            return new InvestigatorFormDto(id, name, workingStatus, assignedAttorneys);
        }
    }
}
