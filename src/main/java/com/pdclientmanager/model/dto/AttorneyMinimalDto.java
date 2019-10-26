package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.entity.WorkingStatus;

public class AttorneyMinimalDto {
    
    private Long id;
    private String name;
    private WorkingStatus workingStatus;
    
    public AttorneyMinimalDto() {
        
    }

    public AttorneyMinimalDto(Long id, String name, WorkingStatus workingStatus) {
        this.id = id;
        this.name = name;
        this.workingStatus = workingStatus;
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

    public static class AttorneyMinimalDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default AttorneyMinimalDto";
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        
        public AttorneyMinimalDtoBuilder withId(Long id) {
            this.id= id;
            return this;
        }
        
        public AttorneyMinimalDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyMinimalDtoBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public AttorneyMinimalDto build() {
            return new AttorneyMinimalDto(id, name, workingStatus);
        }
    }
}
