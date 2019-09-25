package com.pdclientmanager.util;

import java.beans.PropertyEditorSupport;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.service.AttorneyService;

public class AttorneyMinimalDtoEditor extends PropertyEditorSupport {

    AttorneyService attorneyService;
    
    public AttorneyMinimalDtoEditor(AttorneyService attorneyService) {
        this.attorneyService = attorneyService;
    }
    
    @Override
    public String getAsText() {
        AttorneyMinimalDto attorney = (AttorneyMinimalDto) getValue();
        return attorney.getId().toString();
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        AttorneyDto attorney = attorneyService.getById(id);
        AttorneyMinimalDto minimalDto = new AttorneyMinimalDto.AttorneyMinimalDtoBuilder()
                .withId(attorney.getId())
                .withName(attorney.getName())
                .withEmploymentStatus(attorney.getEmploymentStatus())
                .build();
        super.setValue(minimalDto);
    }
}
