package com.pdclientmanager.util.mapper;

import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.repository.entity.Client;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T17:18:22-0400",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.16.0.v20181130-1748, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toClient(ClientForm form, CycleAvoidingMappingContext context) {
        Client target = context.getMappedInstance( form, Client.class );
        if ( target != null ) {
            return target;
        }

        if ( form == null ) {
            return null;
        }

        Client client = new Client();

        context.storeMappedInstance( form, client );

        client.setId( form.getId() );
        client.setName( form.getName() );
        client.setCustodyStatus( form.getCustodyStatus() );

        return client;
    }

    @Override
    public ClientForm toClientForm(Client entity) {
        if ( entity == null ) {
            return null;
        }

        ClientForm clientForm = new ClientForm();

        clientForm.setId( entity.getId() );
        clientForm.setName( entity.getName() );
        clientForm.setCustodyStatus( entity.getCustodyStatus() );

        return clientForm;
    }

    @Override
    public List<Client> toClientList(List<ClientForm> forms) {
        if ( forms == null ) {
            return null;
        }

        List<Client> list = new ArrayList<Client>( forms.size() );
        for ( ClientForm clientForm : forms ) {
            list.add( clientFormToClient( clientForm ) );
        }

        return list;
    }

    @Override
    public List<ClientForm> toClientFormList(List<Client> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClientForm> list = new ArrayList<ClientForm>( entities.size() );
        for ( Client client : entities ) {
            list.add( toClientForm( client ) );
        }

        return list;
    }

    protected Client clientFormToClient(ClientForm clientForm) {
        if ( clientForm == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( clientForm.getId() );
        client.setName( clientForm.getName() );
        client.setCustodyStatus( clientForm.getCustodyStatus() );

        return client;
    }
}
