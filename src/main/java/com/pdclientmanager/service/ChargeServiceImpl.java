package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.entity.Charge;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.util.mapper.ChargeMapper;

@Service
public class ChargeServiceImpl implements ChargeService {

    private ChargeRepository repository;
    private ChargeMapper mapper;
    
    @Autowired
    public ChargeServiceImpl(ChargeRepository repository, ChargeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long save(ChargeDto dto) {
        Charge entity = mapper.toCharge(dto);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public ChargeDto findById(Long targetId) {
        Charge entity = repository.findById(targetId).orElseThrow(EntityNotFoundException::new);
        ChargeDto dto = mapper.toChargeDto(entity);
        return dto;
    }
    
    @Override
    @Transactional
    public List<ChargeDto> findByNameOrStatute(String query) {
        List<ChargeDto> dtoList = mapper.toChargeDtoList(repository.
                findFirst10ByNameContainingOrStatuteContaining(query, query));
        return dtoList;
    }

    @Override
    @Transactional
    public List<ChargeDto> findAll() {
        List<ChargeDto> dtoList = mapper.toChargeDtoList(repository.findAll());
        return dtoList;
    }

    @Override
    @Transactional
    public void delete(ChargeDto dto) {
        Charge entity = mapper.toCharge(dto);
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
