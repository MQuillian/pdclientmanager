package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.repository.entity.Investigator;

@Mapper(uses = {AttorneyResolver.class}, 
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigatorMapper {
    
    //Mapping between InvestigatorForm and Investigator entity
    
    @Mapping(source = "assignedAttorneysIds", target = "assignedAttorneys")
    Investigator toInvestigatorFromInvestigatorForm(final InvestigatorForm form, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "assignedAttorneys", target = "assignedAttorneysIds")
    InvestigatorForm toInvestigatorFormFromInvestigator(final Investigator entity);
    
    List<Investigator> toInvestigatorListFromInvestigatorFormList(final List<InvestigatorForm> forms, @Context CycleAvoidingMappingContext context);
    
    List<InvestigatorForm> toInvestigatorFormListFromInvestigatorList(final List<Investigator> entities);
}
