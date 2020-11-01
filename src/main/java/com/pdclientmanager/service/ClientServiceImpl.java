package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;
import com.pdclientmanager.repository.ClientRepository;
import com.pdclientmanager.repository.entity.Client;
import com.pdclientmanager.util.mapper.ClientMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class ClientServiceImpl implements ClientService {
    
    private ClientRepository repository;
    private ClientMapper mapper;
    
    @Autowired
    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(ClientForm form) {
        Client entity = mapper.toClient(form, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T client = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return client;
    }

    @Override
    @Transactional
    public ClientForm findFormById(Long targetId) {
        Client entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        ClientForm form = mapper.toClientForm(entity);
        return form;
    }
    
    @Override
    @Transactional
    public List<ClientLightProjection> findByName(String query) {
        List<ClientLightProjection> clientList = repository.
                findFirst10ByNameContaining(query);
        return clientList;
    }

    @Override
    @Transactional
    public <T> List<T> findAllBy(Class<T> type) {
        List<T> clientList = repository.findAllBy(type);
        return clientList;
    }

    @Override
    @Transactional
    public void delete(ClientProjection target) {
        deleteById(target.getId());
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }
}
