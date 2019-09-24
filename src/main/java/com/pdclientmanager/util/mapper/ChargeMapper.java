package com.pdclientmanager.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.entity.Charge;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargeMapper {

    //Mapping between ChargeDto and Charge entity
    
    Charge toCharge(final ChargeDto dto);
    
    ChargeDto toChargeDto(final Charge entity);
    
    List<Charge> toChargeList(final List<ChargeDto> dtos);
    
    List<ChargeDto> toChargeDtoList(final List<Charge> entities);
    
}
