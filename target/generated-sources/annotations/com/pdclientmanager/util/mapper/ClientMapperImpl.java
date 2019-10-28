package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientFormDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-27T23:37:13-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class ClientMapperImpl implements ClientMapper {

    @Autowired
    private CaseResolver caseResolver;

    @Override
    public Client toClient(ClientDto dto, CycleAvoidingMappingContext context) {
        Client target = context.getMappedInstance( dto, Client.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        context.storeMappedInstance( dto, client );

        client.setId( dto.getId() );
        client.setName( dto.getName() );
        client.setCustodyStatus( dto.getCustodyStatus() );
        client.setCases( caseMinimalDtoListToCaseList( dto.getCases(), context ) );

        return client;
    }

    @Override
    public ClientDto toClientDto(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId( entity.getId() );
        clientDto.setName( entity.getName() );
        clientDto.setCustodyStatus( entity.getCustodyStatus() );
        clientDto.setCases( caseListToCaseMinimalDtoList( entity.getCases() ) );

        return clientDto;
    }

    @Override
    public List<Client> toClientList(List<ClientDto> dtos, CycleAvoidingMappingContext context) {
        List<Client> target = context.getMappedInstance( dtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtos == null ) {
            return null;
        }

        List<Client> list = new ArrayList<Client>( dtos.size() );
        context.storeMappedInstance( dtos, list );

        for ( ClientDto clientDto : dtos ) {
            list.add( toClient( clientDto, context ) );
        }

        return list;
    }

    @Override
    public List<ClientDto> toClientDtoList(List<Client> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClientDto> list = new ArrayList<ClientDto>( entities.size() );
        for ( Client client : entities ) {
            list.add( toClientDto( client ) );
        }

        return list;
    }

    @Override
    public Client toClientFromClientFormDto(ClientFormDto formDto, CycleAvoidingMappingContext context) {
        Client target = context.getMappedInstance( formDto, Client.class );
        if ( target != null ) {
            return target;
        }

        if ( formDto == null ) {
            return null;
        }

        Client client = new Client();

        context.storeMappedInstance( formDto, client );

        client.setCases( longListToCaseList( formDto.getCasesIds(), context ) );
        client.setId( formDto.getId() );
        client.setName( formDto.getName() );
        client.setCustodyStatus( formDto.getCustodyStatus() );

        return client;
    }

    @Override
    public ClientFormDto toClientFormDtoFromClient(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientFormDto clientFormDto = new ClientFormDto();

        clientFormDto.setCasesIds( caseListToLongList( entity.getCases() ) );
        clientFormDto.setId( entity.getId() );
        clientFormDto.setName( entity.getName() );
        clientFormDto.setCustodyStatus( entity.getCustodyStatus() );

        return clientFormDto;
    }

    @Override
    public ClientMinimalDto toClientMinimalDto(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientMinimalDto clientMinimalDto = new ClientMinimalDto();

        clientMinimalDto.setId( entity.getId() );
        clientMinimalDto.setName( entity.getName() );
        clientMinimalDto.setCustodyStatus( entity.getCustodyStatus() );

        return clientMinimalDto;
    }

    @Override
    public List<ClientMinimalDto> toClientMinimalDtoList(List<ClientMinimalDto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClientMinimalDto> list = new ArrayList<ClientMinimalDto>( entities.size() );
        for ( ClientMinimalDto clientMinimalDto : entities ) {
            list.add( clientMinimalDto );
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

        case1.setId( caseMinimalDto.getId() );
        case1.setCaseNumber( caseMinimalDto.getCaseNumber() );
        case1.setCaseStatus( caseMinimalDto.getCaseStatus() );
        case1.setDateOpened( caseMinimalDto.getDateOpened() );
        case1.setDateClosed( caseMinimalDto.getDateClosed() );
        case1.setChargedCounts( integerChargedCountDtoMapToIntegerChargedCountMap( caseMinimalDto.getChargedCounts(), context ) );

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

    protected CaseMinimalDto caseToCaseMinimalDto(Case case1) {
        if ( case1 == null ) {
            return null;
        }

        CaseMinimalDto caseMinimalDto = new CaseMinimalDto();

        caseMinimalDto.setId( case1.getId() );
        caseMinimalDto.setCaseNumber( case1.getCaseNumber() );
        caseMinimalDto.setCaseStatus( case1.getCaseStatus() );
        caseMinimalDto.setDateOpened( case1.getDateOpened() );
        caseMinimalDto.setDateClosed( case1.getDateClosed() );
        caseMinimalDto.setChargedCounts( integerChargedCountMapToIntegerChargedCountDtoMap( case1.getChargedCounts() ) );

        return caseMinimalDto;
    }

    protected List<CaseMinimalDto> caseListToCaseMinimalDtoList(List<Case> list) {
        if ( list == null ) {
            return null;
        }

        List<CaseMinimalDto> list1 = new ArrayList<CaseMinimalDto>( list.size() );
        for ( Case case1 : list ) {
            list1.add( caseToCaseMinimalDto( case1 ) );
        }

        return list1;
    }

    protected List<Case> longListToCaseList(List<Long> list, CycleAvoidingMappingContext context) {
        List<Case> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Case> list1 = new ArrayList<Case>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Long long1 : list ) {
            list1.add( caseResolver.resolve( long1, Case.class ) );
        }

        return list1;
    }

    protected List<Long> caseListToLongList(List<Case> list) {
        if ( list == null ) {
            return null;
        }

        List<Long> list1 = new ArrayList<Long>( list.size() );
        for ( Case case1 : list ) {
            list1.add( caseResolver.toLong( case1 ) );
        }

        return list1;
    }
}
