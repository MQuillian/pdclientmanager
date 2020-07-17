package com.pdclientmanager.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pdclientmanager.repository.entity.WorkingStatus;

public class AttorneyForm {

    private Long id;
    
    @NotEmpty
    private String name;
    
    @NotNull
    private WorkingStatus workingStatus;
    
    private Long investigatorId;
    
    public AttorneyForm() {
        
    }

    public AttorneyForm(Long id, String name, WorkingStatus workingStatus, Long investigatorId) {
        this.id = id;
        this.name = name;
        this.workingStatus = workingStatus;
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

    public WorkingStatus getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(WorkingStatus workingStatus) {
        this.workingStatus = workingStatus;
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
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        private Long investigatorId = 1L;
        
        public AttorneyFormDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public AttorneyFormDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyFormDtoBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public AttorneyFormDtoBuilder withInvestigatorId(Long investigatorId) {
            this.investigatorId = investigatorId;
            return this;
        }
        
        public AttorneyForm build() {
            return new AttorneyForm(id, name, workingStatus, investigatorId);
        }
    }
}
