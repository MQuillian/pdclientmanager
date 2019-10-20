package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Client;
import com.pdclientmanager.model.entity.Judge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-20T18:23:08-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class CaseMapperImpl implements CaseMapper {

    @Override
    public Case toCase(CaseDto dto, CycleAvoidingMappingContext context) {
        Case target = context.getMappedInstance( dto, Case.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Case case1 = new Case();

        context.storeMappedInstance( dto, case1 );

        case1.setId( dto.getId() );
        case1.setCaseNumber( dto.getCaseNumber() );
        case1.setCaseStatus( dto.getCaseStatus() );
        case1.setDateOpened( dto.getDateOpened() );
        case1.setDateClosed( dto.getDateClosed() );
        case1.setClient( clientMinimalDtoToClient( dto.getClient(), context ) );
        case1.setJudge( judgeDtoToJudge( dto.getJudge(), context ) );
        case1.setAttorney( attorneyMinimalDtoToAttorney( dto.getAttorney(), context ) );
        case1.setChargedCounts( integerChargedCountDtoMapToIntegerChargedCountMap( dto.getChargedCounts(), context ) );

        return case1;
    }

    @Override
    public CaseDto toCaseDto(Case entity) {
        if ( entity == null ) {
            return null;
        }

        CaseDto caseDto = new CaseDto();

        caseDto.setId( entity.getId() );
        caseDto.setCaseNumber( entity.getCaseNumber() );
        caseDto.setCaseStatus( entity.getCaseStatus() );
        caseDto.setDateOpened( entity.getDateOpened() );
        caseDto.setDateClosed( entity.getDateClosed() );
        caseDto.setClient( clientToClientMinimalDto( entity.getClient() ) );
        caseDto.setJudge( judgeToJudgeDto( entity.getJudge() ) );
        caseDto.setAttorney( attorneyToAttorneyMinimalDto( entity.getAttorney() ) );
        caseDto.setChargedCounts( integerChargedCountMapToIntegerChargedCountDtoMap( entity.getChargedCounts() ) );

        return caseDto;
    }

    @Override
    public List<Case> toCaseList(List<CaseDto> dtos, CycleAvoidingMappingContext context) {
        List<Case> target = context.getMappedInstance( dtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtos == null ) {
            return null;
        }

        List<Case> list = new ArrayList<Case>( dtos.size() );
        context.storeMappedInstance( dtos, list );

        for ( CaseDto caseDto : dtos ) {
            list.add( toCase( caseDto, context ) );
        }

        return list;
    }

    @Override
    public List<CaseDto> toCaseDtoList(List<Case> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CaseDto> list = new ArrayList<CaseDto>( entities.size() );
        for ( Case case1 : entities ) {
            list.add( toCaseDto( case1 ) );
        }

        return list;
    }

    @Override
    public CaseMinimalDto toCaseMinimalDto(Case entity) {
        if ( entity == null ) {
            return null;
        }

        CaseMinimalDto caseMinimalDto = new CaseMinimalDto();

        caseMinimalDto.setId( entity.getId() );
        caseMinimalDto.setCaseNumber( entity.getCaseNumber() );
        caseMinimalDto.setCaseStatus( entity.getCaseStatus() );
        caseMinimalDto.setDateOpened( entity.getDateOpened() );
        caseMinimalDto.setDateClosed( entity.getDateClosed() );
        caseMinimalDto.setChargedCounts( integerChargedCountMapToIntegerChargedCountDtoMap( entity.getChargedCounts() ) );

        return caseMinimalDto;
    }

    @Override
    public List<CaseMinimalDto> toCaseMinimalDtoList(List<Case> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CaseMinimalDto> list = new ArrayList<CaseMinimalDto>( entities.size() );
        for ( Case case1 : entities ) {
            list.add( toCaseMinimalDto( case1 ) );
        }

        return list;
    }

    protected Client clientMinimalDtoToClient(ClientMinimalDto clientMinimalDto, CycleAvoidingMappingContext context) {
        Client target = context.getMappedInstance( clientMinimalDto, Client.class );
        if ( target != null ) {
            return target;
        }

        if ( clientMinimalDto == null ) {
            return null;
        }

        Client client = new Client();

        context.storeMappedInstance( clientMinimalDto, client );

        client.setId( clientMinimalDto.getId() );
        client.setName( clientMinimalDto.getName() );
        client.setCustodyStatus( clientMinimalDto.getCustodyStatus() );

        return client;
    }

    protected Judge judgeDtoToJudge(JudgeDto judgeDto, CycleAvoidingMappingContext context) {
        Judge target = context.getMappedInstance( judgeDto, Judge.class );
        if ( target != null ) {
            return target;
        }

        if ( judgeDto == null ) {
            return null;
        }

        Judge judge = new Judge();

        context.storeMappedInstance( judgeDto, judge );

        judge.setId( judgeDto.getId() );
        judge.setName( judgeDto.getName() );

        return judge;
    }

    protected Attorney attorneyMinimalDtoToAttorney(AttorneyMinimalDto attorneyMinimalDto, CycleAvoidingMappingContext context) {
        Attorney target = context.getMappedInstance( attorneyMinimalDto, Attorney.class );
        if ( target != null ) {
            return target;
        }

        if ( attorneyMinimalDto == null ) {
            return null;
        }

        Attorney attorney = new Attorney();

        context.storeMappedInstance( attorneyMinimalDto, attorney );

        attorney.setId( attorneyMinimalDto.getId() );
        attorney.setName( attorneyMinimalDto.getName() );
        attorney.setEmploymentStatus( attorneyMinimalDto.getEmploymentStatus() );

        return attorney;
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

        chargedCount.setId( chargedCountDto.getId() );
        chargedCount.setCountNumber( chargedCountDto.getCountNumber() );
        chargedCount.setCharge( chargeDtoToCharge( chargedCountDto.getCharge(), context ) );

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

    protected ClientMinimalDto clientToClientMinimalDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientMinimalDto clientMinimalDto = new ClientMinimalDto();

        clientMinimalDto.setId( client.getId() );
        clientMinimalDto.setName( client.getName() );
        clientMinimalDto.setCustodyStatus( client.getCustodyStatus() );

        return clientMinimalDto;
    }

    protected JudgeDto judgeToJudgeDto(Judge judge) {
        if ( judge == null ) {
            return null;
        }

        JudgeDto judgeDto = new JudgeDto();

        judgeDto.setId( judge.getId() );
        judgeDto.setName( judge.getName() );

        return judgeDto;
    }

    protected AttorneyMinimalDto attorneyToAttorneyMinimalDto(Attorney attorney) {
        if ( attorney == null ) {
            return null;
        }

        AttorneyMinimalDto attorneyMinimalDto = new AttorneyMinimalDto();

        attorneyMinimalDto.setId( attorney.getId() );
        attorneyMinimalDto.setName( attorney.getName() );
        attorneyMinimalDto.setEmploymentStatus( attorney.getEmploymentStatus() );

        return attorneyMinimalDto;
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

    protected ChargedCountDto chargedCountToChargedCountDto(ChargedCount chargedCount) {
        if ( chargedCount == null ) {
            return null;
        }

        ChargedCountDto chargedCountDto = new ChargedCountDto();

        chargedCountDto.setId( chargedCount.getId() );
        if ( chargedCount.getCountNumber() != null ) {
            chargedCountDto.setCountNumber( chargedCount.getCountNumber() );
        }
        chargedCountDto.setCharge( chargeToChargeDto( chargedCount.getCharge() ) );

        return chargedCountDto;
    }

    protected Map<Integer, ChargedCountDto> integerChargedCountMapToIntegerChargedCountDtoMap(Map<Integer, ChargedCount> map) {
        if ( map == null ) {
            return null;
        }

        Map<Integer, ChargedCountDto> map1 = new HashMap<Integer, ChargedCountDto>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Integer, ChargedCount> entry : map.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCountDto value = chargedCountToChargedCountDto( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
