package com.pdclientmanager.model;

import java.beans.PropertyEditorSupport;

import com.pdclientmanager.service.CrudService;

public class InvestigatorEditor extends PropertyEditorSupport {

    CrudService<Investigator> investigatorService;
    
    public InvestigatorEditor(CrudService<Investigator> service) {
        this.investigatorService = service;
    }
    
    @Override
    public String getAsText() {
        if((Investigator) getValue() != null) {
            Investigator investigator = (Investigator) getValue();
            return investigator.getId().toString();
        }
        return null;
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        Investigator investigator = investigatorService.getById(id);
        super.setValue(investigator);
    }
}
