package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.ChargedCount;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargedCountMapper {
    
    // Mapping between ChargedCountDto and ChargedCount entity

    ChargedCount toChargedCountFromChargedCountDto(final ChargedCountDto dto);
    
    @Mapping(target = "countNumber", source = "id.countNumber")
    ChargedCountDto toChargedCountDtoFromChargedCount(final ChargedCount entity);
    
    List<ChargedCount> toChargedCountListFromDtoList(final List<ChargedCountDto> dtos);
    
    List<ChargedCountDto> toChargedCountDtoListFromEntityList(final List<ChargedCount> entities);
}
