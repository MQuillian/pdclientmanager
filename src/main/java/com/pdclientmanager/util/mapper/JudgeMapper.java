package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Judge;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JudgeMapper {

    //Mapping between JudgeDto and Judge entity
    
    Judge toJudge(final JudgeDto dto);
    
    JudgeDto toJudgeDto(final Judge entity);
    
    List<Judge> toJudgeList(final List<JudgeDto> dtos);
    
    List<JudgeDto> toJudgeDtoList(final List<Judge> entities);
    
}
