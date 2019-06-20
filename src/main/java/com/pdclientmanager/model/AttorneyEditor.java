package com.pdclientmanager.model;

import java.beans.PropertyEditorSupport;

import com.pdclientmanager.service.CrudService;

public class AttorneyEditor extends PropertyEditorSupport {

    CrudService<Attorney> attorneyService;
    
    public AttorneyEditor(CrudService<Attorney> service) {
        this.attorneyService = service;
    }
    
    @Override
    public String getAsText() {
        Attorney attorney = (Attorney) getValue();
        return attorney.getId().toString();
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        Attorney attorney = attorneyService.getById(id);
        super.setValue(attorney);
    }
}
