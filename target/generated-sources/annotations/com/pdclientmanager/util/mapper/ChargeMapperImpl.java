package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.entity.Charge;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-27T23:37:06-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class ChargeMapperImpl implements ChargeMapper {

    @Override
    public Charge toCharge(ChargeDto dto) {
        if ( dto == null ) {
            return null;
        }

        Charge charge = new Charge();

        charge.setId( dto.getId() );
        charge.setName( dto.getName() );
        charge.setStatute( dto.getStatute() );

        return charge;
    }

    @Override
    public ChargeDto toChargeDto(Charge entity) {
        if ( entity == null ) {
            return null;
        }

        ChargeDto chargeDto = new ChargeDto();

        chargeDto.setId( entity.getId() );
        chargeDto.setName( entity.getName() );
        chargeDto.setStatute( entity.getStatute() );

        return chargeDto;
    }

    @Override
    public List<Charge> toChargeList(List<ChargeDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Charge> list = new ArrayList<Charge>( dtos.size() );
        for ( ChargeDto chargeDto : dtos ) {
            list.add( toCharge( chargeDto ) );
        }

        return list;
    }

    @Override
    public List<ChargeDto> toChargeDtoList(List<Charge> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ChargeDto> list = new ArrayList<ChargeDto>( entities.size() );
        for ( Charge charge : entities ) {
            list.add( toChargeDto( charge ) );
        }

        return list;
    }
}
