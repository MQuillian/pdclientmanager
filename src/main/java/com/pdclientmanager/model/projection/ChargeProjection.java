package com.pdclientmanager.model.projection;

public interface ChargeProjection {

    Long getId();
    String getName();
    String getStatute();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setStatute(String statute);
}
