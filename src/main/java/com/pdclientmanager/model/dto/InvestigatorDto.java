package com.pdclientmanager.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.pdclientmanager.model.entity.EmploymentStatus;

public class InvestigatorDto {

    private Long id;
    private String name;
    private EmploymentStatus employmentStatus;
    private List<AttorneyMinimalDto> assignedAttorneys;
    
    public InvestigatorDto() {
        
    }

    public InvestigatorDto(Long id, String name, EmploymentStatus employmentStatus, 
            List<AttorneyMinimalDto> assignedAttorneys) {
        this.id = id;
        this.name = name;
        this.employmentStatus = employmentStatus;
        this.assignedAttorneys = assignedAttorneys;
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

    public List<AttorneyMinimalDto> getAssignedAttorneys() {
        return assignedAttorneys;
    }

    public void setAssignedAttorneys(List<AttorneyMinimalDto> assignedAttorneys) {
        this.assignedAttorneys = assignedAttorneys;
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
        if (!(obj instanceof InvestigatorDto))
            return false;
        InvestigatorDto other = (InvestigatorDto) obj;
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

    public static class InvestigatorDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default InvestigatorDto";
        private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
        private List<AttorneyMinimalDto> assignedAttorneys = new ArrayList<>();
        
        public InvestigatorDtoBuilder() {
            
        }
        
        public InvestigatorDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public InvestigatorDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public InvestigatorDtoBuilder withEmploymentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }
        
        public InvestigatorDtoBuilder withAssignedAttorneys(List<AttorneyMinimalDto> assignedAttorneys) {
            this.assignedAttorneys = assignedAttorneys;
            return this;
        }
        
        public InvestigatorDto build() {
            return new InvestigatorDto(id, name, employmentStatus, assignedAttorneys);
        }
    }
}
