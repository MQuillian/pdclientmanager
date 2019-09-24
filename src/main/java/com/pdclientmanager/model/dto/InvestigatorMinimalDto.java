package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class InvestigatorMinimalDto {

    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    
    public InvestigatorMinimalDto() {
        
    }

    public InvestigatorMinimalDto(Long id, String name, EmploymentStatus employmentStatus) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employmentStatus == null) ? 0 : employmentStatus.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof InvestigatorMinimalDto))
            return false;
        InvestigatorMinimalDto other = (InvestigatorMinimalDto) obj;
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
    
    
    
    @Override
    public String toString() {
        return name;
    }



    public static class InvestigatorMinimalDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default InvestigatorMinimalDto";
        private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        
        public InvestigatorMinimalDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorMinimalDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorMinimalDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }

        public InvestigatorMinimalDto build() {
            return new InvestigatorMinimalDto(id, name, employmentStatus);
        }
    }
}
