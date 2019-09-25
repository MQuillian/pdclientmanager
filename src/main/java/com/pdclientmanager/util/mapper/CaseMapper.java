package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.entity.Case;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaseMapper {
    
    //Mapping between CaseDto and Case entity
    
    Case toCase(final CaseDto dto, @Context CycleAvoidingMappingContext context);
    
    CaseDto toCaseDto(final Case entity, @Context CycleAvoidingMappingContext context);
    
    List<Case> toCaseList(final List<CaseDto> dtos, @Context CycleAvoidingMappingContext context);

    List<CaseDto> toCaseDtoList(final List<Case> entities, @Context CycleAvoidingMappingContext context);

    
    //Mapping between CaseMinimalDto and Case entity
    
    CaseMinimalDto toCaseMinimalDto(final Case entity);
    
    List<CaseMinimalDto> toCaseMinimalDtoList(final List<Case> entities);
}
