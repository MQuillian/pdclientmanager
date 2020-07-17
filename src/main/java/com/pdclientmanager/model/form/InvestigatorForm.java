package com.pdclientmanager.model.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pdclientmanager.repository.entity.WorkingStatus;

public class InvestigatorForm {

    private Long id;
    
    @NotEmpty
    private String name;
    
    @NotNull
    private WorkingStatus workingStatus;
    
    private List<Long> assignedAttorneysIds;
    
    public InvestigatorForm() {
        
    }

    public InvestigatorForm(Long id, String name, WorkingStatus workingStatus,
            List<Long> assignedAttorneysIds) {
        this.id = id;
        this.name = name;
        this.workingStatus = workingStatus;
        this.assignedAttorneysIds = assignedAttorneysIds;
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

    public List<Long> getAssignedAttorneysIds() {
        return assignedAttorneysIds;
    }

    public void setAssignedAttorneysIds(List<Long> assignedAttorneysIds) {
        this.assignedAttorneysIds = assignedAttorneysIds;
    }
    
    @Override
    public String toString() {
        return "InvestigatorForm [id=" + id + ", name=" + name + ", workingStatus=" + workingStatus
                + ", assignedAttorneysIds=" + assignedAttorneysIds + "]";
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
        
        public InvestigatorForm build() {
            return new InvestigatorForm(id, name, workingStatus, assignedAttorneys);
        }
    }
}
