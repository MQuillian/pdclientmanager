package com.pdclientmanager.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.repository.entity.Judge;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JudgeMapper {
    
    //Mapping between JudgeForm and Judge entity
    
    Judge toJudge(final JudgeForm dto);
    
    JudgeForm toJudgeForm(final Judge entity);
    
}
