package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.ChargedCountId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T13:58:59-0500",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class ChargedCountMapperImpl implements ChargedCountMapper {

    @Override
    public ChargedCount toChargedCountFromChargedCountDto(ChargedCountDto dto) {
        if ( dto == null ) {
            return null;
        }

        ChargedCount chargedCount = new ChargedCount();

        chargedCount.setCharge( chargeDtoToCharge( dto.getCharge() ) );

        return chargedCount;
    }

    @Override
    public ChargedCountDto toChargedCountDtoFromChargedCount(ChargedCount entity) {
        if ( entity == null ) {
            return null;
        }

        ChargedCountDto chargedCountDto = new ChargedCountDto();

        Integer countNumber = entityIdCountNumber( entity );
        if ( countNumber != null ) {
            chargedCountDto.setCountNumber( countNumber );
        }
        chargedCountDto.setCharge( chargeToChargeDto( entity.getCharge() ) );

        return chargedCountDto;
    }

    @Override
    public List<ChargedCount> toChargedCountListFromDtoList(List<ChargedCountDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ChargedCount> list = new ArrayList<ChargedCount>( dtos.size() );
        for ( ChargedCountDto chargedCountDto : dtos ) {
            list.add( toChargedCountFromChargedCountDto( chargedCountDto ) );
        }

        return list;
    }

    @Override
    public List<ChargedCountDto> toChargedCountDtoListFromEntityList(List<ChargedCount> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ChargedCountDto> list = new ArrayList<ChargedCountDto>( entities.size() );
        for ( ChargedCount chargedCount : entities ) {
            list.add( toChargedCountDtoFromChargedCount( chargedCount ) );
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

    private Integer entityIdCountNumber(ChargedCount chargedCount) {
        if ( chargedCount == null ) {
            return null;
        }
        ChargedCountId id = chargedCount.getId();
        if ( id == null ) {
            return null;
        }
        Integer countNumber = id.getCountNumber();
        if ( countNumber == null ) {
            return null;
        }
        return countNumber;
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
