package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.CaseMinimalDto;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ChargedCountDto;
import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.model.entity.ChargedCount;
import com.pdclientmanager.model.entity.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-18T23:17:54-0500",
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

        client.setCases( caseMinimalDtoListToCaseList( dto.getCases(), context ) );
        client.setCustodyStatus( dto.getCustodyStatus() );
        client.setId( dto.getId() );
        client.setName( dto.getName() );

        return client;
    }

    @Override
    public ClientDto toClientDto(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setCases( caseListToCaseMinimalDtoList( entity.getCases() ) );
        clientDto.setCustodyStatus( entity.getCustodyStatus() );
        clientDto.setId( entity.getId() );
        clientDto.setName( entity.getName() );

        return clientDto;
    }

    @Override
    public List<Client> toClientList(List<ClientDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Client> list = new ArrayList<Client>( dtos.size() );
        for ( ClientDto clientDto : dtos ) {
            list.add( clientDtoToClient( clientDto ) );
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
    public Client toClientFromClientMinimalDto(ClientMinimalDto dto, CycleAvoidingMappingContext context) {
        Client target = context.getMappedInstance( dto, Client.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        context.storeMappedInstance( dto, client );

        client.setCustodyStatus( dto.getCustodyStatus() );
        client.setId( dto.getId() );
        client.setName( dto.getName() );

        return client;
    }

    @Override
    public List<ClientMinimalDto> toClientMinimalDtoList(List<Client> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClientMinimalDto> list = new ArrayList<ClientMinimalDto>( entities.size() );
        for ( Client client : entities ) {
            list.add( toClientMinimalDto( client ) );
        }

        return list;
    }

    @Override
    public List<Client> toClientListFromClientMinimalDtoList(List<ClientMinimalDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Client> list = new ArrayList<Client>( dtos.size() );
        for ( ClientMinimalDto clientMinimalDto : dtos ) {
            list.add( clientMinimalDtoToClient( clientMinimalDto ) );
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

    protected SortedMap<Integer, ChargedCount> integerChargedCountDtoMapToIntegerChargedCountSortedMap(Map<Integer, ChargedCountDto> map, CycleAvoidingMappingContext context) {
        SortedMap<Integer, ChargedCount> target = context.getMappedInstance( map, SortedMap.class );
        if ( target != null ) {
            return target;
        }

        if ( map == null ) {
            return null;
        }

        SortedMap<Integer, ChargedCount> sortedMap = new TreeMap<Integer, ChargedCount>();

        context.storeMappedInstance( map, sortedMap );

        for ( java.util.Map.Entry<Integer, ChargedCountDto> entry : map.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCount value = chargedCountDtoToChargedCount( entry.getValue(), context );
            sortedMap.put( key, value );
        }

        return sortedMap;
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
        case1.setDateOpened( caseMinimalDto.getDateOpened() );
        case1.setDateClosed( caseMinimalDto.getDateClosed() );
        case1.setChargedCounts( integerChargedCountDtoMapToIntegerChargedCountSortedMap( caseMinimalDto.getChargedCounts(), context ) );

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

        chargedCountDto.setCharge( chargeToChargeDto( chargedCount.getCharge() ) );
        if ( chargedCount.getCountNumber() != null ) {
            chargedCountDto.setCountNumber( chargedCount.getCountNumber() );
        }
        chargedCountDto.setId( chargedCount.getId() );

        return chargedCountDto;
    }

    protected Map<Integer, ChargedCountDto> integerChargedCountSortedMapToIntegerChargedCountDtoMap(SortedMap<Integer, ChargedCount> sortedMap) {
        if ( sortedMap == null ) {
            return null;
        }

        Map<Integer, ChargedCountDto> map = new HashMap<Integer, ChargedCountDto>( Math.max( (int) ( sortedMap.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Integer, ChargedCount> entry : sortedMap.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCountDto value = chargedCountToChargedCountDto( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    protected CaseMinimalDto caseToCaseMinimalDto(Case case1) {
        if ( case1 == null ) {
            return null;
        }

        CaseMinimalDto caseMinimalDto = new CaseMinimalDto();

        caseMinimalDto.setId( case1.getId() );
        caseMinimalDto.setCaseNumber( case1.getCaseNumber() );
        caseMinimalDto.setDateOpened( case1.getDateOpened() );
        caseMinimalDto.setDateClosed( case1.getDateClosed() );
        caseMinimalDto.setChargedCounts( integerChargedCountSortedMapToIntegerChargedCountDtoMap( case1.getChargedCounts() ) );

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

    protected Charge chargeDtoToCharge1(ChargeDto chargeDto) {
        if ( chargeDto == null ) {
            return null;
        }

        Charge charge = new Charge();

        charge.setId( chargeDto.getId() );
        charge.setName( chargeDto.getName() );
        charge.setStatute( chargeDto.getStatute() );

        return charge;
    }

    protected ChargedCount chargedCountDtoToChargedCount1(ChargedCountDto chargedCountDto) {
        if ( chargedCountDto == null ) {
            return null;
        }

        ChargedCount chargedCount = new ChargedCount();

        chargedCount.setId( chargedCountDto.getId() );
        chargedCount.setCountNumber( chargedCountDto.getCountNumber() );
        chargedCount.setCharge( chargeDtoToCharge1( chargedCountDto.getCharge() ) );

        return chargedCount;
    }

    protected SortedMap<Integer, ChargedCount> integerChargedCountDtoMapToIntegerChargedCountSortedMap1(Map<Integer, ChargedCountDto> map) {
        if ( map == null ) {
            return null;
        }

        SortedMap<Integer, ChargedCount> sortedMap = new TreeMap<Integer, ChargedCount>();

        for ( java.util.Map.Entry<Integer, ChargedCountDto> entry : map.entrySet() ) {
            Integer key = entry.getKey();
            ChargedCount value = chargedCountDtoToChargedCount1( entry.getValue() );
            sortedMap.put( key, value );
        }

        return sortedMap;
    }

    protected Case caseMinimalDtoToCase1(CaseMinimalDto caseMinimalDto) {
        if ( caseMinimalDto == null ) {
            return null;
        }

        Case case1 = new Case();

        case1.setId( caseMinimalDto.getId() );
        case1.setCaseNumber( caseMinimalDto.getCaseNumber() );
        case1.setDateOpened( caseMinimalDto.getDateOpened() );
        case1.setDateClosed( caseMinimalDto.getDateClosed() );
        case1.setChargedCounts( integerChargedCountDtoMapToIntegerChargedCountSortedMap1( caseMinimalDto.getChargedCounts() ) );

        return case1;
    }

    protected List<Case> caseMinimalDtoListToCaseList1(List<CaseMinimalDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Case> list1 = new ArrayList<Case>( list.size() );
        for ( CaseMinimalDto caseMinimalDto : list ) {
            list1.add( caseMinimalDtoToCase1( caseMinimalDto ) );
        }

        return list1;
    }

    protected Client clientDtoToClient(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setCases( caseMinimalDtoListToCaseList1( clientDto.getCases() ) );
        client.setCustodyStatus( clientDto.getCustodyStatus() );
        client.setId( clientDto.getId() );
        client.setName( clientDto.getName() );

        return client;
    }

    protected Client clientMinimalDtoToClient(ClientMinimalDto clientMinimalDto) {
        if ( clientMinimalDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setCustodyStatus( clientMinimalDto.getCustodyStatus() );
        client.setId( clientMinimalDto.getId() );
        client.setName( clientMinimalDto.getName() );

        return client;
    }
}
