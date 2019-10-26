package com.pdclientmanager.model.dto;

import com.pdclientmanager.model.entity.WorkingStatus;

public class JudgeDto {

    private Long id;
    private String name;
    private WorkingStatus workingStatus;
    
    public JudgeDto() {
        
    }

    public JudgeDto(Long id, String name, WorkingStatus workingStatus) {
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
        return "JudgeDto [id=" + id + ", name=" + name + ", workingStatus=" + workingStatus + "]";
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
        if (!(obj instanceof JudgeDto))
            return false;
        JudgeDto other = (JudgeDto) obj;
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

    public static class JudgeDtoBuilder {
        
        private Long id = 1L;
        private String name = "Default JudgeDto";
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        
        public JudgeDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public JudgeDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public JudgeDtoBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public JudgeDto build() {
            return new JudgeDto(id, name, workingStatus);
        }
    }
}
