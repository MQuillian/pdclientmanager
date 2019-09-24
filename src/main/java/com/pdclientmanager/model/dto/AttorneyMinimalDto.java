package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class AttorneyMinimalDto {
    
    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    
    public AttorneyMinimalDto() {
        
    }

    public AttorneyMinimalDto(Long id, String name, EmploymentStatus employmentStatus) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
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

    public static class AttorneyMinimalDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default AttorneyMinimalDto";
        private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        
        public AttorneyMinimalDtoBuilder withId(Long id) {
            this.id= id;
            return this;
        }
        
        public AttorneyMinimalDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public AttorneyMinimalDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public AttorneyMinimalDto build() {
            return new AttorneyMinimalDto(id, name, employmentStatus);
        }
    }
}
