package com.pdclientmanager.util.mapper;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.util.CustomDateTimeFormatter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ClientResolver.class, JudgeResolver.class, AttorneyResolver.class})
@DecoratedWith(CaseMapperDecorator.class)
public interface CaseMapper {
    
    // Mapping between CaseFormDto and Case entity
    
//    @Mapping(source = "clientId", target = "client")
//    @Mapping(source = "judgeId", target = "judge")
//    @Mapping(source = "attorneyId", target = "attorney")
    Case toCaseFromCaseFormDto (final CaseForm dto, @Context CycleAvoidingMappingContext context);
    
    @Mapping(source = "client", target = "clientId")
    @Mapping(source = "judge", target = "judgeId")
    @Mapping(source = "attorney", target = "attorneyId")
    CaseForm toCaseFormDtoFromCase (final Case entity);
    
    List<Case> toCaseListFromCaseFormDtoList (final List<CaseForm> dtos, @Context CycleAvoidingMappingContext context);
    
    List<CaseForm> toCaseFormDtoListFromCaseList (final List<Case> entities);
    
    
    // Custom default method for mapping between String and LocalDate
    
    default LocalDate toLocalDateFromString(String date) {
        if(date.equals("")) {
            return null;
        }
        CustomDateTimeFormatter dateTimeFormatter = new CustomDateTimeFormatter();
        return dateTimeFormatter.parse(date);
    }
}
