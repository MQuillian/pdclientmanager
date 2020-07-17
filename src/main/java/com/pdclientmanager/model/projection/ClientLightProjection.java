package com.pdclientmanager.model.projection;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface ClientLightProjection {

    Long getId();
    String getName();
    CustodyStatus getCustodyStatus();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setCustodyStatus(CustodyStatus status);
}
