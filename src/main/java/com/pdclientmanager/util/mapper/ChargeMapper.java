package com.pdclientmanager.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.repository.entity.Charge;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargeMapper {

    //Mapping between ChargeForm and Charge entity
    
    Charge toCharge(final ChargeForm form);
    
    ChargeForm toChargeForm(final Charge entity);
}
