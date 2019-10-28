package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.dto.InvestigatorMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-27T23:37:09-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
*/
@Component
public class InvestigatorMapperImpl implements InvestigatorMapper {

    @Autowired
    private AttorneyResolver attorneyResolver;

    @Override
    public Investigator toInvestigator(InvestigatorDto dto, CycleAvoidingMappingContext context) {
        Investigator target = context.getMappedInstance( dto, Investigator.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Investigator investigator = new Investigator();

        context.storeMappedInstance( dto, investigator );

        investigator.setId( dto.getId() );
        investigator.setName( dto.getName() );
        investigator.setWorkingStatus( dto.getWorkingStatus() );
        investigator.setAssignedAttorneys( attorneyMinimalDtoListToAttorneyList( dto.getAssignedAttorneys(), context ) );

        return investigator;
    }

    @Override
    public InvestigatorDto toInvestigatorDto(Investigator entity) {
        if ( entity == null ) {
            return null;
        }

        InvestigatorDto investigatorDto = new InvestigatorDto();

        investigatorDto.setId( entity.getId() );
        investigatorDto.setName( entity.getName() );
        investigatorDto.setWorkingStatus( entity.getWorkingStatus() );
        investigatorDto.setAssignedAttorneys( attorneyListToAttorneyMinimalDtoList( entity.getAssignedAttorneys() ) );

        return investigatorDto;
    }

    @Override
    public List<Investigator> toInvestigatorList(List<InvestigatorDto> dtos, CycleAvoidingMappingContext context) {
        List<Investigator> target = context.getMappedInstance( dtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtos == null ) {
            return null;
        }

        List<Investigator> list = new ArrayList<Investigator>( dtos.size() );
        context.storeMappedInstance( dtos, list );

        for ( InvestigatorDto investigatorDto : dtos ) {
            list.add( toInvestigator( investigatorDto, context ) );
        }

        return list;
    }

    @Override
    public List<InvestigatorDto> toInvestigatorDtoList(List<Investigator> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InvestigatorDto> list = new ArrayList<InvestigatorDto>( entities.size() );
        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorDto( investigator ) );
        }

        return list;
    }

    @Override
    public Investigator toInvestigatorFromInvestigatorFormDto(InvestigatorFormDto dto, CycleAvoidingMappingContext context) {
        Investigator target = context.getMappedInstance( dto, Investigator.class );
        if ( target != null ) {
            return target;
        }

        if ( dto == null ) {
            return null;
        }

        Investigator investigator = new Investigator();

        context.storeMappedInstance( dto, investigator );

        investigator.setAssignedAttorneys( longListToAttorneyList( dto.getAssignedAttorneysIds(), context ) );
        investigator.setId( dto.getId() );
        investigator.setName( dto.getName() );
        investigator.setWorkingStatus( dto.getWorkingStatus() );

        return investigator;
    }

    @Override
    public InvestigatorFormDto toInvestigatorFormDtoFromInvestigator(Investigator entity) {
        if ( entity == null ) {
            return null;
        }

        InvestigatorFormDto investigatorFormDto = new InvestigatorFormDto();

        investigatorFormDto.setAssignedAttorneysIds( attorneyListToLongList( entity.getAssignedAttorneys() ) );
        investigatorFormDto.setId( entity.getId() );
        investigatorFormDto.setName( entity.getName() );
        investigatorFormDto.setWorkingStatus( entity.getWorkingStatus() );

        return investigatorFormDto;
    }

    @Override
    public List<Investigator> toInvestigatorListFromInvestigatorFormDtoList(List<InvestigatorFormDto> formDtos, CycleAvoidingMappingContext context) {
        List<Investigator> target = context.getMappedInstance( formDtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( formDtos == null ) {
            return null;
        }

        List<Investigator> list = new ArrayList<Investigator>( formDtos.size() );
        context.storeMappedInstance( formDtos, list );

        for ( InvestigatorFormDto investigatorFormDto : formDtos ) {
            list.add( toInvestigatorFromInvestigatorFormDto( investigatorFormDto, context ) );
        }

        return list;
    }

    @Override
    public List<InvestigatorFormDto> toInvestigatorFormDtoListFromInvestigatorList(List<Investigator> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InvestigatorFormDto> list = new ArrayList<InvestigatorFormDto>( entities.size() );
        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorFormDtoFromInvestigator( investigator ) );
        }

        return list;
    }

    @Override
    public InvestigatorMinimalDto toInvestigatorMinimalDtoFromInvestigator(Investigator entity) {
        if ( entity == null ) {
            return null;
        }

        InvestigatorMinimalDto investigatorMinimalDto = new InvestigatorMinimalDto();

        investigatorMinimalDto.setId( entity.getId() );
        investigatorMinimalDto.setName( entity.getName() );
        investigatorMinimalDto.setWorkingStatus( entity.getWorkingStatus() );

        return investigatorMinimalDto;
    }

    @Override
    public List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromInvestigatorList(List<Investigator> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InvestigatorMinimalDto> list = new ArrayList<InvestigatorMinimalDto>( entities.size() );
        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorMinimalDtoFromInvestigator( investigator ) );
        }

        return list;
    }

    @Override
    public InvestigatorMinimalDto toInvestigatorMinimalDtoFromInvestigatorDto(InvestigatorDto fullDto) {
        if ( fullDto == null ) {
            return null;
        }

        InvestigatorMinimalDto investigatorMinimalDto = new InvestigatorMinimalDto();

        investigatorMinimalDto.setId( fullDto.getId() );
        investigatorMinimalDto.setName( fullDto.getName() );
        investigatorMinimalDto.setWorkingStatus( fullDto.getWorkingStatus() );

        return investigatorMinimalDto;
    }

    @Override
    public List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromInvestigatorDtoList(List<InvestigatorDto> fullDtoList) {
        if ( fullDtoList == null ) {
            return null;
        }

        List<InvestigatorMinimalDto> list = new ArrayList<InvestigatorMinimalDto>( fullDtoList.size() );
        for ( InvestigatorDto investigatorDto : fullDtoList ) {
            list.add( toInvestigatorMinimalDtoFromInvestigatorDto( investigatorDto ) );
        }

        return list;
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
        attorney.setWorkingStatus( attorneyMinimalDto.getWorkingStatus() );

        return attorney;
    }

    protected List<Attorney> attorneyMinimalDtoListToAttorneyList(List<AttorneyMinimalDto> list, CycleAvoidingMappingContext context) {
        List<Attorney> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Attorney> list1 = new ArrayList<Attorney>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( AttorneyMinimalDto attorneyMinimalDto : list ) {
            list1.add( attorneyMinimalDtoToAttorney( attorneyMinimalDto, context ) );
        }

        return list1;
    }

    protected AttorneyMinimalDto attorneyToAttorneyMinimalDto(Attorney attorney) {
        if ( attorney == null ) {
            return null;
        }

        AttorneyMinimalDto attorneyMinimalDto = new AttorneyMinimalDto();

        attorneyMinimalDto.setId( attorney.getId() );
        attorneyMinimalDto.setName( attorney.getName() );
        attorneyMinimalDto.setWorkingStatus( attorney.getWorkingStatus() );

        return attorneyMinimalDto;
    }

    protected List<AttorneyMinimalDto> attorneyListToAttorneyMinimalDtoList(List<Attorney> list) {
        if ( list == null ) {
            return null;
        }

        List<AttorneyMinimalDto> list1 = new ArrayList<AttorneyMinimalDto>( list.size() );
        for ( Attorney attorney : list ) {
            list1.add( attorneyToAttorneyMinimalDto( attorney ) );
        }

        return list1;
    }

    protected List<Attorney> longListToAttorneyList(List<Long> list, CycleAvoidingMappingContext context) {
        List<Attorney> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Attorney> list1 = new ArrayList<Attorney>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Long long1 : list ) {
            list1.add( attorneyResolver.resolve( long1, Attorney.class ) );
        }

        return list1;
    }

    protected List<Long> attorneyListToLongList(List<Attorney> list) {
        if ( list == null ) {
            return null;
        }

        List<Long> list1 = new ArrayList<Long>( list.size() );
        for ( Attorney attorney : list ) {
            list1.add( attorneyResolver.toLong( attorney ) );
        }

        return list1;
    }
}
