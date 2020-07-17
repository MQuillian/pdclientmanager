package com.pdclientmanager.model.projection;

import com.pdclientmanager.repository.entity.WorkingStatus;

public interface JudgeProjection {

    Long getId();
    String getName();
    WorkingStatus getWorkingStatus();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setWorkingStatus(WorkingStatus status);
}
