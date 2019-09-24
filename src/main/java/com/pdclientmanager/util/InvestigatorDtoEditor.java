package com.pdclientmanager.util;

import java.beans.PropertyEditorSupport;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.service.InvestigatorService;

public class InvestigatorDtoEditor extends PropertyEditorSupport {

    InvestigatorService investigatorService;
    
    public InvestigatorDtoEditor(InvestigatorService investigatorService) {
        this.investigatorService = investigatorService;
    }
    
    @Override
    public String getAsText() {
        if((InvestigatorDto) getValue() != null) {
            InvestigatorDto investigator = (InvestigatorDto) getValue();
            return investigator.getId().toString();
        }
        return null;
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        InvestigatorDto investigator = investigatorService.getById(id);
        super.setValue(investigator);
    }
}
