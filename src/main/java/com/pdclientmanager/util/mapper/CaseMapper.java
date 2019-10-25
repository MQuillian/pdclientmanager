package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.entity.Case;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ClientResolver.class, JudgeResolver.class, AttorneyResolver.class})
public interface CaseMapper {
    
    // Mapping between CaseDto and Case entity
    
    Case toCase(final CaseDto dto, @Context CycleAvoidingMappingContext context);
    
    CaseDto toCaseDto(final Case entity);
    
    List<Case> toCaseList(final List<CaseDto> dtos, @Context CycleAvoidingMappingContext context);

    List<CaseDto> toCaseDtoList(final List<Case> entities);

    
    // Mapping between CaseFormDto and Case entity
    
    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "judgeId", target = "judge")
    @Mapping(source = "attorneyId", target = "attorney")
    Case toCaseFromCaseFormDto (final CaseFormDto dto, @Context CycleAvoidingMappingContext context);
    
    @Mapping(source = "client", target = "clientId")
    @Mapping(source = "judge", target = "judgeId")
    @Mapping(source = "attorney", target = "attorneyId")
    CaseFormDto toCaseFormDtoFromCase (final Case entity);
    
    List<Case> toCaseListFromCaseFormDtoList (final List<CaseFormDto> dtos, @Context CycleAvoidingMappingContext context);
    
    List<CaseFormDto> toCaseFormDtoListFromCaseList (final List<Case> entities);
    
    
    // Mapping between CaseMinimalDto and Case entity
    
    CaseMinimalDto toCaseMinimalDto(final Case entity);
    
    List<CaseMinimalDto> toCaseMinimalDtoList(final List<Case> entities);
    
    
    //Mapping between CaseMinimalDto and CaseDto
    
    CaseMinimalDto toCaseMinimalDtoFromCaseDto(final CaseDto dto);
    
    List<CaseMinimalDto> toCaseMinimalDtoListFromCaseDtoList(final List<CaseDto> dtos);
}
