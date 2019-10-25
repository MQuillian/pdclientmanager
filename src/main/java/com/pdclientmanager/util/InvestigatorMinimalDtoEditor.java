package com.pdclientmanager.util;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.service.InvestigatorService;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

public class InvestigatorMinimalDtoEditor extends PropertyEditorSupport {
    
    InvestigatorService investigatorService;
    
    @Autowired
    InvestigatorMapper mapper;
    
    public InvestigatorMinimalDtoEditor(InvestigatorService investigatorService) {
        this.investigatorService = investigatorService;
    }
    
    @Override
    public String getAsText() {
        if((InvestigatorMinimalDto) getValue() != null) {
            InvestigatorMinimalDto investigator = (InvestigatorMinimalDto) getValue();
            return investigator.getId().toString();
        }
        return null;
    }

    @Override
    public void setAsText(String targetId) throws IllegalArgumentException {
        Long id = Long.valueOf(targetId);
        InvestigatorDto investigator = investigatorService.findById(id);
        InvestigatorMinimalDto minimalDto = mapper.toInvestigatorMinimalDtoFromInvestigatorDto(investigator);
        super.setValue(minimalDto);
    }
}
