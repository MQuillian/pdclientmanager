package com.pdclientmanager.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pdclientmanager.repository.entity.WorkingStatus;

public class JudgeForm {

    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private WorkingStatus workingStatus;
    
    public JudgeForm() {
        
    }

    public JudgeForm(Long id, String name, WorkingStatus workingStatus) {
        this.id = id;
        this.name = name;
        this.workingStatus = workingStatus;
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

    
    
    @Override
    public String toString() {
        return "JudgeForm [id=" + id + ", name=" + name + ", workingStatus=" + workingStatus + "]";
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
        if (!(obj instanceof JudgeForm))
            return false;
        JudgeForm other = (JudgeForm) obj;
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

    public static class JudgeFormBuilder {
        
        private Long id = 1L;
        private String name = "Default JudgeForm";
        private WorkingStatus workingStatus = WorkingStatus.ACTIVE;
        
        public JudgeFormBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public JudgeFormBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public JudgeFormBuilder withWorkingStatus(WorkingStatus workingStatus) {
            this.workingStatus = workingStatus;
            return this;
        }
        
        public JudgeForm build() {
            return new JudgeForm(id, name, workingStatus);
        }
    }
}
