package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.entity.Attorney;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {InvestigatorResolver.class})
public interface AttorneyMapper {
    
    //Mapping between AttorneyDto and Attorney entity
    
    Attorney toAttorney(final AttorneyDto dto, @Context CycleAvoidingMappingContext context);
    
    AttorneyDto toAttorneyDto(final Attorney entity);
    
    List<Attorney> toAttorneyList(final List<AttorneyDto> dtos, @Context CycleAvoidingMappingContext context);

    List<AttorneyDto> toAttorneyDtoList(final List<Attorney> entities);
    
    
    //Mapping between AttorneyFormDto and Attorney entity
    
    @Mapping(source = "investigatorId", target = "investigator")
    Attorney toAttorneyFromAttorneyFormDto(final AttorneyFormDto dto, @Context CycleAvoidingMappingContext context);
    
    @Mapping(source = "investigator", target = "investigatorId")
    AttorneyFormDto toAttorneyFormDtoFromAttorney(final Attorney entity);
    
    List<Attorney> toAttorneyListFromAttorneyFormDtoList(final List<AttorneyFormDto> formDtos, @Context CycleAvoidingMappingContext context);
    
    List<AttorneyFormDto> toAttorneyFormDtoListFromAttorney(final List<Attorney> entities);
    
    
    //Mapping between AttorneyMinimalDto and Attorney entity
    
    AttorneyMinimalDto toAttorneyMinimalDtoFromAttorney(final Attorney entity);
    
    List<AttorneyMinimalDto> toAttorneyMinimalDtoList(final List<Attorney> entities);
    
    
    //Mapping between AttorneyDto and AttorneyMinimalDto
    
    AttorneyMinimalDto toAttorneyMinimalDtoFromAttorneyDto(final AttorneyDto fullDto);
    
    List<AttorneyMinimalDto> toAttorneyMinimalDtoListFromAttorneyDtoList(final List<AttorneyDto> fullDtoList);
}
