package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.ChargedCount;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargedCountMapper {
    
    //Mapping between ChargedCountDto and ChargedCount entity

    ChargedCount toChargedCount(final ChargedCountDto dto);
    
    ChargedCountDto toChargedCountDto(final ChargedCount entity);
    
    List<ChargedCount> toChargedCountList(final List<ChargedCountDto> dtos);
    
    List<ChargedCountDto> toChargedCountDtoList(final List<ChargedCount> entities);
}
