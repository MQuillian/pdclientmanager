package com.pdclientmanager.util.mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.repository.entity.ChargedCount;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.service.AttorneyService;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.service.JudgeService;
import com.pdclientmanager.util.CustomDateTimeFormatter;

// Decorator class for CaseMapper that provides mapping support for SortedMap
//   fields of Case, CaseProjection, and CaseForm

public class CaseMapperDecorator implements CaseMapper {

    @Autowired
    @Qualifier("delegate")
    private CaseMapper delegate;
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private JudgeService judgeService;
    
    @Autowired
    private AttorneyService attorneyService;
    
    @Autowired
    private ChargeService chargeService;
    
    private CustomDateTimeFormatter dateFormatter = new CustomDateTimeFormatter();

    @Override
    public Case toCaseFromCaseFormDto(CaseForm dto, CycleAvoidingMappingContext context) {
        Case entity = delegate.toCaseFromCaseFormDto(dto, context);
        
        entity.setClient(clientService.findById(dto.getClientId(), Client.class));
        entity.setJudge(judgeService.findById(dto.getJudgeId(), Judge.class));
        entity.setAttorney(attorneyService.findById(dto.getAttorneyId(), Attorney.class));
        entity.setChargedCounts(new TreeMap<>());
        
        for(Map.Entry<Integer, Long> entry : dto.getChargedCountsIds().entrySet()) {
            Charge charge = chargeService.findById(entry.getValue(), Charge.class);
            
            ChargedCount chargedCount = new ChargedCount(entry.getKey(), charge);
            
            entity.addChargedCount(chargedCount);
        }
        
        return entity;
    }

    @Override
    public CaseForm toCaseFormDtoFromCase(Case entity) {
        CaseForm dto = delegate.toCaseFormDtoFromCase(entity);
        dto.setDateOpened(dateFormatter
                .toFormattedDateString(entity.getDateOpened()));
        dto.setDateClosed(dateFormatter
                .toFormattedDateString(entity.getDateClosed()));
        dto.setChargedCountsIds(new TreeMap<>());
        dto.setChargedCountsStrings(new TreeMap<>());
        
        for(Map.Entry<Integer, ChargedCount> entry : entity.getChargedCounts().entrySet()) {
            dto.addChargedCount(entry.getValue());
        }
        
        dto.setClientName(entity.getClient().getName());
        
        
        return dto;
    }

    @Override
    public List<Case> toCaseListFromCaseFormDtoList(List<CaseForm> dtos, CycleAvoidingMappingContext context) {
        return delegate.toCaseListFromCaseFormDtoList(dtos, context);
    }

    @Override
    public List<CaseForm> toCaseFormDtoListFromCaseList(List<Case> entities) {
        return delegate.toCaseFormDtoListFromCaseList(entities);
    }
}