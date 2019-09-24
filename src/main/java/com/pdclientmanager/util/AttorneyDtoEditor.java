package com.pdclientmanager.util;

import java.beans.PropertyEditorSupport;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.service.AttorneyService;

public class AttorneyDtoEditor extends PropertyEditorSupport {

    AttorneyService attorneyService;
    
    public AttorneyDtoEditor(AttorneyService attorneyService) {
        this.attorneyService = attorneyService;
    }
    
    @Override
    public String getAsText() {
        AttorneyDto attorney = (AttorneyDto) getValue();
        return attorney.getId().toString();
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        AttorneyDto attorney = attorneyService.getById(id);
        super.setValue(attorney);
    }
}
