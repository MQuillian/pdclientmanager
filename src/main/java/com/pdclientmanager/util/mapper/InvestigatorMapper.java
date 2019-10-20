package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    
    InvestigatorDto toInvestigatorDto(final Investigator entity);
    
    List<Investigator> toInvestigatorList(final List<InvestigatorDto> dtos, @Context CycleAvoidingMappingContext context);
    
    List<InvestigatorDto> toInvestigatorDtoList(final List<Investigator> entities);
    
    
    //Mapping between InvestigatorFormDto and Investigator entity
    
    @Mapping(source = "assignedAttorneyIds", target = "assignedAttorneys")
    Investigator toInvestigatorFromInvestigatorFormDto(final InvestigatorFormDto dto, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "assignedAttorneys", target = "assignedAttorneyIds")
    InvestigatorFormDto toInvestigatorFormDtoFromInvestigator(final Investigator entity);
    
    List<Investigator> toInvestigatorListFromInvestigatorFormDtoList(final List<InvestigatorFormDto> formDtos, @Context CycleAvoidingMappingContext context);
    
    List<InvestigatorFormDto> toInvestigatorFormDtoListFromInvestigatorList(final List<Investigator> entities);
    
    
    //Mapping between InvestigatorMinimalDto and Investigator entity
    
    InvestigatorMinimalDto toInvestigatorMinimalDtoFromInvestigator(final Investigator entity);
    
    List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromInvestigatorList(final List<Investigator> entities);
    
    
    //Mapping between InvestigatorDto and InvestigatorMinimalDto
    
    InvestigatorMinimalDto toInvestigatorMinimalDtoFromInvestigatorDto(final InvestigatorDto fullDto);
    
    List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromInvestigatorDtoList(final List<InvestigatorDto> fullDtoList);
}
