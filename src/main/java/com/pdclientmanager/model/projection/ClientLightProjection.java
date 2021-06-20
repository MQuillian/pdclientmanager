package com.pdclientmanager.model.projection;

import java.time.LocalDate;

import com.pdclientmanager.repository.entity.CustodyStatus;

public interface ClientLightProjection {

    Long getId();
    String getName();
    CustodyStatus getCustodyStatus();
    LocalDate incarcerationDate();
    LocalDate releaseDate();
    
    // setters for testing
    void setId(Long id);
    void setName(String name);
    void setCustodyStatus(CustodyStatus status);
    void setIncarcerationDate(LocalDate incarcerationDate);
    void setReleaseDate(LocalDate releaseDate);
}
