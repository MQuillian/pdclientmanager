package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Investigator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-09-23T19:22:58-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class AttorneyMapperImpl implements AttorneyMapper {

    @Autowired
    private InvestigatorResolver investigatorResolver;

    @Override
    public Attorney toAttorney(AttorneyDto dto, CycleAvoidingMappingContext context) {
        Attorney target = context.getMappedInstance( dto, Attorney.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Attorney attorney = new Attorney();

        context.storeMappedInstance( dto, attorney );

        attorney.setEmploymentStatus( dto.getEmploymentStatus() );
        attorney.setId( dto.getId() );
        attorney.setName( dto.getName() );
        attorney.setCaseload( caseMinimalDtoListToCaseList( dto.getCaseload(), context ) );
        attorney.setInvestigator( investigatorMinimalDtoToInvestigator( dto.getInvestigator(), context ) );

        return attorney;
    }

    @Override
    public AttorneyDto toAttorneyDto(Attorney entity, CycleAvoidingMappingContext context) {
        AttorneyDto target = context.getMappedInstance( entity, AttorneyDto.class );
        if ( target != null ) {
            return target;
        }

        if ( entity == null ) {
            return null;
        }

        AttorneyDto attorneyDto = new AttorneyDto();

        context.storeMappedInstance( entity, attorneyDto );

        attorneyDto.setCaseload( caseListToCaseMinimalDtoList( entity.getCaseload(), context ) );
        attorneyDto.setEmploymentStatus( entity.getEmploymentStatus() );
        attorneyDto.setId( entity.getId() );
        attorneyDto.setInvestigator( investigatorToInvestigatorMinimalDto( entity.getInvestigator(), context ) );
        attorneyDto.setName( entity.getName() );

        return attorneyDto;
    }

    @Override
    public List<Attorney> toAttorneyList(List<AttorneyDto> dtos, CycleAvoidingMappingContext context) {
        List<Attorney> target = context.getMappedInstance( dtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtos == null ) {
            return null;
        }

        List<Attorney> list = new ArrayList<Attorney>( dtos.size() );
        context.storeMappedInstance( dtos, list );

        for ( AttorneyDto attorneyDto : dtos ) {
            list.add( toAttorney( attorneyDto, context ) );
        }

        return list;
    }

    @Override
    public List<AttorneyDto> toAttorneyDtoList(List<Attorney> entities, CycleAvoidingMappingContext context) {
        List<AttorneyDto> target = context.getMappedInstance( entities, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entities == null ) {
            return null;
        }

        List<AttorneyDto> list = new ArrayList<AttorneyDto>( entities.size() );
        context.storeMappedInstance( entities, list );

        for ( Attorney attorney : entities ) {
            list.add( toAttorneyDto( attorney, context ) );
        }

        return list;
    }

    @Override
    public Attorney toAttorneyFromAttorneyFormDto(AttorneyFormDto dto, CycleAvoidingMappingContext context) {
        Attorney target = context.getMappedInstance( dto, Attorney.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Attorney attorney = new Attorney();

        context.storeMappedInstance( dto, attorney );

        attorney.setInvestigator( investigatorResolver.resolve( dto.getInvestigatorId(), Investigator.class ) );
        attorney.setEmploymentStatus( dto.getEmploymentStatus() );
        attorney.setId( dto.getId() );
        attorney.setName( dto.getName() );

        return attorney;
    }

    @Override
    public AttorneyFormDto toAttorneyFormDtoFromAttorney(Attorney entity) {
        if ( entity == null ) {
            return null;
        }

        AttorneyFormDto attorneyFormDto = new AttorneyFormDto();

        attorneyFormDto.setInvestigatorId( investigatorResolver.toLong( entity.getInvestigator() ) );
        attorneyFormDto.setEmploymentStatus( entity.getEmploymentStatus() );
        attorneyFormDto.setId( entity.getId() );
        attorneyFormDto.setName( entity.getName() );

        return attorneyFormDto;
    }

    @Override
    public List<Attorney> toAttorneyListFromAttorneyFormDtoList(List<AttorneyFormDto> formDtos, CycleAvoidingMappingContext context) {
        List<Attorney> target = context.getMappedInstance( formDtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( formDtos == null ) {
            return null;
        }

        List<Attorney> list = new ArrayList<Attorney>( formDtos.size() );
        context.storeMappedInstance( formDtos, list );

        for ( AttorneyFormDto attorneyFormDto : formDtos ) {
            list.add( toAttorneyFromAttorneyFormDto( attorneyFormDto, context ) );
        }

        return list;
    }

    @Override
    public List<AttorneyFormDto> toAttorneyFormDtoListFromAttorney(List<Attorney> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AttorneyFormDto> list = new ArrayList<AttorneyFormDto>( entities.size() );
        for ( Attorney attorney : entities ) {
            list.add( toAttorneyFormDtoFromAttorney( attorney ) );
        }

        return list;
    }

    protected Charge chargeDtoToCharge(ChargeDto chargeDto, CycleAvoidingMappingContext context) {
        Charge target = context.getMappedInstance( chargeDto, Charge.class );
        if ( target != null ) {
            return target;
        }

        if ( chargeDto == null ) {
            return null;
        }

        Charge charge = new Charge();

        context.storeMappedInstance( chargeDto, charge );

        charge.setId( chargeDto.getId() );
        charge.setName( chargeDto.getName() );
        charge.setStatute( chargeDto.getStatute() );

        return charge;
    }

    protected ChargedCount chargedCountDtoToChargedCount(ChargedCountDto chargedCountDto, CycleAvoidingMappingContext context) {
        ChargedCount target = context.getMappedInstance( chargedCountDto, ChargedCount.class );
        if ( target != null ) {
            return target;
        }

        if ( chargedCountDto == null ) {
            return null;
        }

        ChargedCount chargedCount = new ChargedCount();

        context.storeMappedInstance( chargedCountDto, chargedCount );

        chargedCount.setCharge( chargeDtoToCharge( chargedCountDto.getCharge(), context ) );
        chargedCount.setCountNumber( chargedCountDto.getCountNumber() );
        chargedCount.setId( chargedCountDto.getId() );

        return chargedCount;
    }

    protected Map<Integer, ChargedCount> integerChargedCountDtoMapToIntegerChargedCountMap(Map<Integer, ChargedCountDto> map, CycleAvoidingMappingContext context) {
        Map<Integer, ChargedCount> target = context.getMappedInstance( map, Map.class );
        if ( target != null ) {
            return target;
        }

        if ( map == null ) {
            return null;
        }

        Map<Integer, ChargedCount> map1 = new HashMap<Integer, ChargedCount>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        context.storeMappedInstance( map, map1 );

        for ( java.util.Map.Entry<Integer, ChargedCountDto> entry : map.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCount value = chargedCountDtoToChargedCount( entry.getValue(), context );
            map1.put( key, value );
        }

        return map1;
    }

    protected Case caseMinimalDtoToCase(CaseMinimalDto caseMinimalDto, CycleAvoidingMappingContext context) {
        Case target = context.getMappedInstance( caseMinimalDto, Case.class );
        if ( target != null ) {
            return target;
        }

        if ( caseMinimalDto == null ) {
            return null;
        }

        Case case1 = new Case();

        context.storeMappedInstance( caseMinimalDto, case1 );

        case1.setCaseNumber( caseMinimalDto.getCaseNumber() );
        case1.setCaseStatus( caseMinimalDto.getCaseStatus() );
        case1.setChargedCounts( integerChargedCountDtoMapToIntegerChargedCountMap( caseMinimalDto.getChargedCounts(), context ) );
        case1.setDateClosed( caseMinimalDto.getDateClosed() );
        case1.setDateOpened( caseMinimalDto.getDateOpened() );
        case1.setId( caseMinimalDto.getId() );

        return case1;
    }

    protected List<Case> caseMinimalDtoListToCaseList(List<CaseMinimalDto> list, CycleAvoidingMappingContext context) {
        List<Case> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Case> list1 = new ArrayList<Case>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( CaseMinimalDto caseMinimalDto : list ) {
            list1.add( caseMinimalDtoToCase( caseMinimalDto, context ) );
        }

        return list1;
    }

    protected Investigator investigatorMinimalDtoToInvestigator(InvestigatorMinimalDto investigatorMinimalDto, CycleAvoidingMappingContext context) {
        Investigator target = context.getMappedInstance( investigatorMinimalDto, Investigator.class );
        if ( target != null ) {
            return target;
        }

        if ( investigatorMinimalDto == null ) {
            return null;
        }

        Investigator investigator = new Investigator();

        context.storeMappedInstance( investigatorMinimalDto, investigator );

        investigator.setEmploymentStatus( investigatorMinimalDto.getEmploymentStatus() );
        investigator.setId( investigatorMinimalDto.getId() );
        investigator.setName( investigatorMinimalDto.getName() );

        return investigator;
    }

    protected ChargeDto chargeToChargeDto(Charge charge, CycleAvoidingMappingContext context) {
        ChargeDto target = context.getMappedInstance( charge, ChargeDto.class );
        if ( target != null ) {
            return target;
        }

        if ( charge == null ) {
            return null;
        }

        ChargeDto chargeDto = new ChargeDto();

        context.storeMappedInstance( charge, chargeDto );

        chargeDto.setId( charge.getId() );
        chargeDto.setName( charge.getName() );
        chargeDto.setStatute( charge.getStatute() );

        return chargeDto;
    }

    protected ChargedCountDto chargedCountToChargedCountDto(ChargedCount chargedCount, CycleAvoidingMappingContext context) {
        ChargedCountDto target = context.getMappedInstance( chargedCount, ChargedCountDto.class );
        if ( target != null ) {
            return target;
        }

        if ( chargedCount == null ) {
            return null;
        }

        ChargedCountDto chargedCountDto = new ChargedCountDto();

        context.storeMappedInstance( chargedCount, chargedCountDto );

        chargedCountDto.setCharge( chargeToChargeDto( chargedCount.getCharge(), context ) );
        if ( chargedCount.getCountNumber() != null ) {
            chargedCountDto.setCountNumber( chargedCount.getCountNumber() );
        }
        chargedCountDto.setId( chargedCount.getId() );

        return chargedCountDto;
    }

    protected Map<Integer, ChargedCountDto> integerChargedCountMapToIntegerChargedCountDtoMap(Map<Integer, ChargedCount> map, CycleAvoidingMappingContext context) {
        Map<Integer, ChargedCountDto> target = context.getMappedInstance( map, Map.class );
        if ( target != null ) {
            return target;
        }

        if ( map == null ) {
            return null;
        }

        Map<Integer, ChargedCountDto> map1 = new HashMap<Integer, ChargedCountDto>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        context.storeMappedInstance( map, map1 );

        for ( java.util.Map.Entry<Integer, ChargedCount> entry : map.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCountDto value = chargedCountToChargedCountDto( entry.getValue(), context );
            map1.put( key, value );
        }

        return map1;
    }

    protected CaseMinimalDto caseToCaseMinimalDto(Case case1, CycleAvoidingMappingContext context) {
        CaseMinimalDto target = context.getMappedInstance( case1, CaseMinimalDto.class );
        if ( target != null ) {
            return target;
        }

        if ( case1 == null ) {
            return null;
        }

        CaseMinimalDto caseMinimalDto = new CaseMinimalDto();

        context.storeMappedInstance( case1, caseMinimalDto );

        caseMinimalDto.setCaseNumber( case1.getCaseNumber() );
        caseMinimalDto.setCaseStatus( case1.getCaseStatus() );
        caseMinimalDto.setChargedCounts( integerChargedCountMapToIntegerChargedCountDtoMap( case1.getChargedCounts(), context ) );
        caseMinimalDto.setDateClosed( case1.getDateClosed() );
        caseMinimalDto.setDateOpened( case1.getDateOpened() );
        caseMinimalDto.setId( case1.getId() );

        return caseMinimalDto;
    }

    protected List<CaseMinimalDto> caseListToCaseMinimalDtoList(List<Case> list, CycleAvoidingMappingContext context) {
        List<CaseMinimalDto> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<CaseMinimalDto> list1 = new ArrayList<CaseMinimalDto>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Case case1 : list ) {
            list1.add( caseToCaseMinimalDto( case1, context ) );
        }

        return list1;
    }

    protected InvestigatorMinimalDto investigatorToInvestigatorMinimalDto(Investigator investigator, CycleAvoidingMappingContext context) {
        InvestigatorMinimalDto target = context.getMappedInstance( investigator, InvestigatorMinimalDto.class );
        if ( target != null ) {
            return target;
        }

        if ( investigator == null ) {
            return null;
        }

        InvestigatorMinimalDto investigatorMinimalDto = new InvestigatorMinimalDto();

        context.storeMappedInstance( investigator, investigatorMinimalDto );

        investigatorMinimalDto.setEmploymentStatus( investigator.getEmploymentStatus() );
        investigatorMinimalDto.setId( investigator.getId() );
        investigatorMinimalDto.setName( investigator.getName() );

        return investigatorMinimalDto;
    }
}
