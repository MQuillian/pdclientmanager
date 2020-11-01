package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Investigator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T17:18:18-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class InvestigatorMapperImpl implements InvestigatorMapper {

    @Autowired
    private AttorneyResolver attorneyResolver;

    @Override
    public Investigator toInvestigatorFromInvestigatorForm(InvestigatorForm form, CycleAvoidingMappingContext context) {
        Investigator target = context.getMappedInstance( form, Investigator.class );
        if ( target != null ) {
            return target;
        }

        if ( form == null ) {
            return null;
        }

        Investigator investigator = new Investigator();

        context.storeMappedInstance( form, investigator );

        investigator.setAssignedAttorneys( longListToAttorneyList( form.getAssignedAttorneysIds(), context ) );
        investigator.setId( form.getId() );
        investigator.setName( form.getName() );
        investigator.setWorkingStatus( form.getWorkingStatus() );

        return investigator;
    }

    @Override
    public InvestigatorForm toInvestigatorFormFromInvestigator(Investigator entity) {
        if ( entity == null ) {
            return null;
        }

        InvestigatorForm investigatorForm = new InvestigatorForm();

        investigatorForm.setAssignedAttorneysIds( attorneyListToLongList( entity.getAssignedAttorneys() ) );
        investigatorForm.setId( entity.getId() );
        investigatorForm.setName( entity.getName() );
        investigatorForm.setWorkingStatus( entity.getWorkingStatus() );

        return investigatorForm;
    }

    @Override
    public List<Investigator> toInvestigatorListFromInvestigatorFormList(List<InvestigatorForm> forms, CycleAvoidingMappingContext context) {
        List<Investigator> target = context.getMappedInstance( forms, List.class );
        if ( target != null ) {
            return target;
        }

        if ( forms == null ) {
            return null;
        }

        List<Investigator> list = new ArrayList<Investigator>( forms.size() );
        context.storeMappedInstance( forms, list );

        for ( InvestigatorForm investigatorForm : forms ) {
            list.add( toInvestigatorFromInvestigatorForm( investigatorForm, context ) );
        }

        return list;
    }

    @Override
    public List<InvestigatorForm> toInvestigatorFormListFromInvestigatorList(List<Investigator> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InvestigatorForm> list = new ArrayList<InvestigatorForm>( entities.size() );
        for ( Investigator investigator : entities ) {
            list.add( toInvestigatorFormFromInvestigator( investigator ) );
        }

        return list;
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
