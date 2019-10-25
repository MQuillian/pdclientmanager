package com.pdclientmanager.util.mapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.model.entity.Client;
import com.pdclientmanager.repository.ClientRepository;

@Component
public class ClientResolver {

    private ClientRepository repository;
    
    @Autowired
    public ClientResolver(ClientRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Client resolve(Long clientId, @TargetType Class<Client> type) {
        if(clientId != null) {
            return repository.findById(clientId).orElseThrow(EntityNotFoundException::new);
        } else {
            return new Client();
        }
    }
    
    public Long toLong(Client entity) {
        return entity != null ? entity.getId() : null;
    }
}
