package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Investigator;

@Mapper(uses = {AttorneyResolver.class}, 
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigatorMapper {
    
    //Mapping between InvestigatorDto and Investigator entity

    Investigator toInvestigator(final InvestigatorDto dto, @Context CycleAvoidingMappingContext context);
    
    InvestigatorDto toInvestigatorDto(final Investigator entity, @Context CycleAvoidingMappingContext context);
    
    List<Investigator> toInvestigatorList(final List<InvestigatorDto> dtos, @Context CycleAvoidingMappingContext context);
    
    List<InvestigatorDto> toInvestigatorDtoList(final List<Investigator> entities, @Context CycleAvoidingMappingContext context);
    
    
    //Mapping between InvestigatorFormDto and Investigator entity
    
    Investigator toInvestigatorFromInvestigatorFormDto(final InvestigatorFormDto dto, @Context CycleAvoidingMappingContext context);

    
    //Mapping between InvestigatorMinimalDto and Investigator entity
    
    InvestigatorMinimalDto toInvestigatorMinimalDto(final Investigator entity);
    
    List<InvestigatorMinimalDto> toInvestigatorMinimalDtoList(final List<Investigator> entities);
    
    
    //Mapping between InvestigatorDto and InvestigatorMinimalDto
    
    InvestigatorMinimalDto toInvestigatorMinimalDtoFromFullDto(final InvestigatorDto fullDto);
    
    List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromFullDtoList(final List<InvestigatorDto> fullDtoList);
}
