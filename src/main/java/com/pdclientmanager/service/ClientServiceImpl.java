package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.model.dto.ClientMinimalDto;
import com.pdclientmanager.model.entity.Client;
import com.pdclientmanager.repository.ClientRepository;
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
    public Long save(ClientMinimalDto formDto) {
        Client entity = mapper.toClientFromClientMinimalDto(formDto, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public ClientDto findById(Long targetId) {
        Client entity = repository.findById(targetId).orElseThrow(EntityNotFoundException::new);
        ClientDto dto = mapper.toClientDto(entity);
        return dto;
    }

    @Override
    @Transactional
    public ClientMinimalDto findFormById(Long targetId) {
        Client entity = repository.findById(targetId).orElseThrow(EntityNotFoundException::new);
        ClientMinimalDto formDto = mapper.toClientMinimalDto(entity);
        return formDto;
    }
    
    @Override
    @Transactional
    public List<ClientDto> findByName(String query) {
        List<ClientDto> dtoList = mapper.toClientDtoList(repository.
                findFirst10ByNameContaining(query));
        return dtoList;
    }

    @Override
    @Transactional
    public List<ClientDto> findAll() {
        List<ClientDto> dtoList = mapper.toClientDtoList(repository.findAll());
        return dtoList;
    }

    @Override
    @Transactional
    public void delete(ClientDto dto) {
        Client entity = mapper.toClient(dto, new CycleAvoidingMappingContext());
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
