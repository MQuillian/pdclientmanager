package com.pdclientmanager.util.mapper;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargedCountMapper {
    
    // Mapping between ChargedCountDto and ChargedCount entity

    ChargedCount toChargedCountFromChargedCountDto(final ChargedCountDto dto);
    
    ChargedCountDto toChargedCountDtoFromChargedCount(final ChargedCount entity);
    
    List<ChargedCount> toChargedCountListFromDtoList(final List<ChargedCountDto> dtos);
    
    List<ChargedCountDto> toChargedCountDtoListFromEntityList(final List<ChargedCount> entities);
}
