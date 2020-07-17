package com.pdclientmanager.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Judge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String name;
    
    @NotNull(message = "Please enter employment status")
    private WorkingStatus workingStatus;
    
    public Judge() {
        
    }

    public Judge(Long id, String name, WorkingStatus workingStatus) {
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

    

    @Override
    public String toString() {
        return "Judge [id=" + id + ", name=" + name + ", workingStatus=" + workingStatus + "]";
    }

    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((workingStatus == null) ? 0 : workingStatus.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Judge))
            return false;
        Judge other = (Judge) obj;
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
        if (workingStatus != other.workingStatus)
            return false;
        return true;
    }



    public static class JudgeBuilder {
        
        private Long id = 1L;
        private String name = "Default Judge";
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        
        public JudgeBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public JudgeBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public JudgeBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public Judge build() {
            return new Judge(id, name, workingStatus);
        }
    }
}
