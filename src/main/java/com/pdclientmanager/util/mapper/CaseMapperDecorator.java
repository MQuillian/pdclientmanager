package com.pdclientmanager.util.mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.util.CustomDateTimeFormatter;

// Decorator class for CaseMapper that provides mapping support for SortedMap
//   fields of Case, CaseDto, and CaseFormDto

public class CaseMapperDecorator implements CaseMapper {

    @Autowired
    @Qualifier("delegate")
    private CaseMapper delegate;
    
    @Autowired
    private ChargeRepository chargeRepository;
    
    private CustomDateTimeFormatter dateFormatter = new CustomDateTimeFormatter();
    
    
    @Override
    public Case toCase(CaseDto dto, CycleAvoidingMappingContext context) {
        return delegate.toCase(dto, context);
    }

    @Override
    public CaseDto toCaseDto(Case entity) {
        return delegate.toCaseDto(entity);
    }

    @Override
    public List<Case> toCaseList(List<CaseDto> dtos, CycleAvoidingMappingContext context) {
        return delegate.toCaseList(dtos, context);
    }

    @Override
    public List<CaseDto> toCaseDtoList(List<Case> entities) {
        return delegate.toCaseDtoList(entities);
    }

    @Override
    public Case toCaseFromCaseFormDto(CaseFormDto dto, CycleAvoidingMappingContext context) {
        Case entity = delegate.toCaseFromCaseFormDto(dto, context);
        entity.setChargedCounts(new TreeMap<>());
        
        for(Map.Entry<Integer, Long> entry : dto.getChargedCountsIds().entrySet()) {
            Charge charge = chargeRepository.findById(entry.getValue())
                    .orElseThrow(EntityNotFoundException::new);
            
            ChargedCount chargedCount = new ChargedCount(entry.getKey(), charge, null);
            
            entity.addChargedCount(chargedCount);
        }
        
        return entity;
    }

    @Override
    public CaseFormDto toCaseFormDtoFromCase(Case entity) {
        CaseFormDto dto = delegate.toCaseFormDtoFromCase(entity);
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
    public List<Case> toCaseListFromCaseFormDtoList(List<CaseFormDto> dtos, CycleAvoidingMappingContext context) {
        return delegate.toCaseListFromCaseFormDtoList(dtos, context);
    }

    @Override
    public List<CaseFormDto> toCaseFormDtoListFromCaseList(List<Case> entities) {
        return delegate.toCaseFormDtoListFromCaseList(entities);
    }

    @Override
    public CaseMinimalDto toCaseMinimalDto(Case entity) {
        return delegate.toCaseMinimalDto(entity);
    }

    @Override
    public List<CaseMinimalDto> toCaseMinimalDtoList(List<Case> entities) {
        return delegate.toCaseMinimalDtoList(entities);
    }

    @Override
    public CaseMinimalDto toCaseMinimalDtoFromCaseDto(CaseDto dto) {
        return delegate.toCaseMinimalDtoFromCaseDto(dto);
    }

    @Override
    public List<CaseMinimalDto> toCaseMinimalDtoListFromCaseDtoList(List<CaseDto> dtos) {
        return delegate.toCaseMinimalDtoListFromCaseDtoList(dtos);
    }

}
