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
    date = "2019-09-18T12:20:02-0400",
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

        investigator.setEmploymentStatus( dto.getEmploymentStatus() );
        investigator.setId( dto.getId() );
        investigator.setName( dto.getName() );
        investigator.setAssignedAttorneys( attorneyMinimalDtoListToAttorneyList( dto.getAssignedAttorneys(), context ) );

        return investigator;
    }

    @Override
    public InvestigatorDto toInvestigatorDto(Investigator entity, CycleAvoidingMappingContext context) {
        InvestigatorDto target = context.getMappedInstance( entity, InvestigatorDto.class );
        if ( target != null ) {
            return target;
        }

        if ( entity == null ) {
            return null;
        }

        InvestigatorDto investigatorDto = new InvestigatorDto();

        context.storeMappedInstance( entity, investigatorDto );

        investigatorDto.setAssignedAttorneys( attorneyListToAttorneyMinimalDtoList( entity.getAssignedAttorneys(), context ) );
        investigatorDto.setEmploymentStatus( entity.getEmploymentStatus() );
        investigatorDto.setId( entity.getId() );
        investigatorDto.setName( entity.getName() );

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
    public List<InvestigatorDto> toInvestigatorDtoList(List<Investigator> entities, CycleAvoidingMappingContext context) {
        List<InvestigatorDto> target = context.getMappedInstance( entities, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entities == null ) {
            return null;
        }

        List<InvestigatorDto> list = new ArrayList<InvestigatorDto>( entities.size() );
        context.storeMappedInstance( entities, list );

        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorDto( investigator, context ) );
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

        investigator.setEmploymentStatus( dto.getEmploymentStatus() );
        investigator.setId( dto.getId() );
        investigator.setName( dto.getName() );

        return investigator;
    }

    @Override
    public InvestigatorMinimalDto toInvestigatorMinimalDto(Investigator entity) {
        if ( entity == null ) {
            return null;
        }

        InvestigatorMinimalDto investigatorMinimalDto = new InvestigatorMinimalDto();

        investigatorMinimalDto.setEmploymentStatus( entity.getEmploymentStatus() );
        investigatorMinimalDto.setId( entity.getId() );
        investigatorMinimalDto.setName( entity.getName() );

        return investigatorMinimalDto;
    }

    @Override
    public List<InvestigatorMinimalDto> toInvestigatorMinimalDtoList(List<Investigator> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InvestigatorMinimalDto> list = new ArrayList<InvestigatorMinimalDto>( entities.size() );
        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorMinimalDto( investigator ) );
        }

        return list;
    }

    @Override
    public InvestigatorMinimalDto toInvestigatorMinimalDtoFromFullDto(InvestigatorDto fullDto) {
        if ( fullDto == null ) {
            return null;
        }

        InvestigatorMinimalDto investigatorMinimalDto = new InvestigatorMinimalDto();

        investigatorMinimalDto.setEmploymentStatus( fullDto.getEmploymentStatus() );
        investigatorMinimalDto.setId( fullDto.getId() );
        investigatorMinimalDto.setName( fullDto.getName() );

        return investigatorMinimalDto;
    }

    @Override
    public List<InvestigatorMinimalDto> toInvestigatorMinimalDtoListFromFullDtoList(List<InvestigatorDto> fullDtoList) {
        if ( fullDtoList == null ) {
            return null;
        }

        List<InvestigatorMinimalDto> list = new ArrayList<InvestigatorMinimalDto>( fullDtoList.size() );
        for ( InvestigatorDto investigatorDto : fullDtoList ) {
            list.add( toInvestigatorMinimalDtoFromFullDto( investigatorDto ) );
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

        attorney.setEmploymentStatus( attorneyMinimalDto.getEmploymentStatus() );
        attorney.setId( attorneyMinimalDto.getId() );
        attorney.setName( attorneyMinimalDto.getName() );

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

    protected AttorneyMinimalDto attorneyToAttorneyMinimalDto(Attorney attorney, CycleAvoidingMappingContext context) {
        AttorneyMinimalDto target = context.getMappedInstance( attorney, AttorneyMinimalDto.class );
        if ( target != null ) {
            return target;
        }

        if ( attorney == null ) {
            return null;
        }

        AttorneyMinimalDto attorneyMinimalDto = new AttorneyMinimalDto();

        context.storeMappedInstance( attorney, attorneyMinimalDto );

        attorneyMinimalDto.setEmploymentStatus( attorney.getEmploymentStatus() );
        attorneyMinimalDto.setId( attorney.getId() );
        attorneyMinimalDto.setName( attorney.getName() );

        return attorneyMinimalDto;
    }

    protected List<AttorneyMinimalDto> attorneyListToAttorneyMinimalDtoList(List<Attorney> list, CycleAvoidingMappingContext context) {
        List<AttorneyMinimalDto> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<AttorneyMinimalDto> list1 = new ArrayList<AttorneyMinimalDto>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Attorney attorney : list ) {
            list1.add( attorneyToAttorneyMinimalDto( attorney, context ) );
        }

        return list1;
    }
}
