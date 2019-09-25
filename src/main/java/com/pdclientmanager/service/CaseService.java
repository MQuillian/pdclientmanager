package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.entity.Case;

public interface CaseService extends CrudService<CaseDto, Case> {
    
    public List<CaseDto> getAllWithInitializedClients();

    public List<CaseDto> getAllActiveByAttorneyId(Long attorneyId);
    
}
