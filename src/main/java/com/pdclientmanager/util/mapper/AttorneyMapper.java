package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.entity.Attorney;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {InvestigatorResolver.class})
public interface AttorneyMapper {
    
    //Mapping between AttorneyDto and Attorney entity
    
    Attorney toAttorney(final AttorneyDto dto, @Context CycleAvoidingMappingContext context);
    
    AttorneyDto toAttorneyDto(final Attorney entity, @Context CycleAvoidingMappingContext context);
    
    List<Attorney> toAttorneyList(final List<AttorneyDto> dtos, @Context CycleAvoidingMappingContext context);

    List<AttorneyDto> toAttorneyDtoList(final List<Attorney> entities, @Context CycleAvoidingMappingContext context);
    
    
    //Mapping between AttorneyFormDto and Attorney entity
    
    @Mapping(source = "investigatorId", target = "investigator")
    Attorney toAttorneyFromAttorneyFormDto(final AttorneyFormDto dto, @Context CycleAvoidingMappingContext context);
    
    @Mapping(source = "investigator", target = "investigatorId")
    AttorneyFormDto toAttorneyFormDtoFromAttorney(final Attorney entity);
    
    List<Attorney> toAttorneyListFromAttorneyFormDtoList(final List<AttorneyFormDto> formDtos, @Context CycleAvoidingMappingContext context);
    
    List<AttorneyFormDto> toAttorneyFormDtoListFromAttorney(final List<Attorney> entities);
}
