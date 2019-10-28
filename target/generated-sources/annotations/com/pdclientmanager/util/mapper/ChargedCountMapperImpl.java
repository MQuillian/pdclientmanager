package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-27T23:37:07-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class ChargedCountMapperImpl implements ChargedCountMapper {

    @Override
    public ChargedCount toChargedCount(ChargedCountDto dto) {
        if ( dto == null ) {
            return null;
        }

        ChargedCount chargedCount = new ChargedCount();

        chargedCount.setId( dto.getId() );
        chargedCount.setCountNumber( dto.getCountNumber() );
        chargedCount.setCharge( chargeDtoToCharge( dto.getCharge() ) );

        return chargedCount;
    }

    @Override
    public ChargedCountDto toChargedCountDto(ChargedCount entity) {
        if ( entity == null ) {
            return null;
        }

        ChargedCountDto chargedCountDto = new ChargedCountDto();

        chargedCountDto.setId( entity.getId() );
        if ( entity.getCountNumber() != null ) {
            chargedCountDto.setCountNumber( entity.getCountNumber() );
        }
        chargedCountDto.setCharge( chargeToChargeDto( entity.getCharge() ) );

        return chargedCountDto;
    }

    @Override
    public List<ChargedCount> toChargedCountList(List<ChargedCountDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ChargedCount> list = new ArrayList<ChargedCount>( dtos.size() );
        for ( ChargedCountDto chargedCountDto : dtos ) {
            list.add( toChargedCount( chargedCountDto ) );
        }

        return list;
    }

    @Override
    public List<ChargedCountDto> toChargedCountDtoList(List<ChargedCount> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ChargedCountDto> list = new ArrayList<ChargedCountDto>( entities.size() );
        for ( ChargedCount chargedCount : entities ) {
            list.add( toChargedCountDto( chargedCount ) );
        }

        return list;
    }

    protected Charge chargeDtoToCharge(ChargeDto chargeDto) {
        if ( chargeDto == null ) {
            return null;
        }

        Charge charge = new Charge();

        charge.setId( chargeDto.getId() );
        charge.setName( chargeDto.getName() );
        charge.setStatute( chargeDto.getStatute() );

        return charge;
    }

    protected ChargeDto chargeToChargeDto(Charge charge) {
        if ( charge == null ) {
            return null;
        }

        ChargeDto chargeDto = new ChargeDto();

        chargeDto.setId( charge.getId() );
        chargeDto.setName( charge.getName() );
        chargeDto.setStatute( charge.getStatute() );

        return chargeDto;
    }
}
