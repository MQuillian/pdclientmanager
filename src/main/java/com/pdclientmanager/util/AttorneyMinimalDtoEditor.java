package com.pdclientmanager.util;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.util.mapper.AttorneyMapper;

public class AttorneyMinimalDtoEditor extends PropertyEditorSupport {

    private AttorneyService attorneyService;
    
    @Autowired
    private AttorneyMapper mapper;
    
    
    
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
        AttorneyMinimalDto minimalDto = mapper.toAttorneyMinimalDtoFromAttorneyDto(attorney);
        super.setValue(minimalDto);
    }
}
