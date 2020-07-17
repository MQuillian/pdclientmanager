package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.repository.entity.Attorney;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {InvestigatorResolver.class})
public interface AttorneyMapper {
    
    // Mapping between AttorneyForm and Attorney entity
    
    @Mapping(source = "investigatorId", target = "investigator")
    Attorney toAttorneyFromAttorneyFormDto(final AttorneyForm dto, @Context CycleAvoidingMappingContext context);
    
    @Mapping(source = "investigator", target = "investigatorId")
    AttorneyForm toAttorneyFormDtoFromAttorney(final Attorney entity);
    
    List<Attorney> toAttorneyListFromAttorneyFormDtoList(final List<AttorneyForm> dtos, @Context CycleAvoidingMappingContext context);
    
    List<AttorneyForm> toAttorneyFormDtoListFromAttorney(final List<Attorney> entities);
}
