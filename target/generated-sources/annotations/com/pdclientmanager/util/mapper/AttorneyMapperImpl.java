package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Investigator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T17:18:19-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class AttorneyMapperImpl implements AttorneyMapper {

    @Autowired
    private InvestigatorResolver investigatorResolver;

    @Override
    public Attorney toAttorneyFromAttorneyFormDto(AttorneyForm dto, CycleAvoidingMappingContext context) {
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
        attorney.setId( dto.getId() );
        attorney.setName( dto.getName() );
        attorney.setWorkingStatus( dto.getWorkingStatus() );

        return attorney;
    }

    @Override
    public AttorneyForm toAttorneyFormDtoFromAttorney(Attorney entity) {
        if ( entity == null ) {
            return null;
        }

        AttorneyForm attorneyForm = new AttorneyForm();

        attorneyForm.setInvestigatorId( investigatorResolver.toLong( entity.getInvestigator() ) );
        attorneyForm.setId( entity.getId() );
        attorneyForm.setName( entity.getName() );
        attorneyForm.setWorkingStatus( entity.getWorkingStatus() );

        return attorneyForm;
    }

    @Override
    public List<Attorney> toAttorneyListFromAttorneyFormDtoList(List<AttorneyForm> dtos, CycleAvoidingMappingContext context) {
        List<Attorney> target = context.getMappedInstance( dtos, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtos == null ) {
            return null;
        }

        List<Attorney> list = new ArrayList<Attorney>( dtos.size() );
        context.storeMappedInstance( dtos, list );

        for ( AttorneyForm attorneyForm : dtos ) {
            list.add( toAttorneyFromAttorneyFormDto( attorneyForm, context ) );
        }

        return list;
    }

    @Override
    public List<AttorneyForm> toAttorneyFormDtoListFromAttorney(List<Attorney> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AttorneyForm> list = new ArrayList<AttorneyForm>( entities.size() );
        for ( Attorney attorney : entities ) {
            list.add( toAttorneyFormDtoFromAttorney( attorney ) );
        }

        return list;
    }
}
