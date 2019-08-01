package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.Case;

public interface CaseService extends CrudService<Case> {
    
    public List<Case> getAllWithInitializedClients();

    public List<Case> getAllActiveByAttorneyIdWithInitializedClient(Long attorneyId);
    
}
