package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.dto.AttorneyMinimalDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.WorkingStatus;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class AttorneyServiceImpl implements AttorneyService {

    private AttorneyRepository repository;
    private AttorneyMapper mapper;
    
    @Autowired
    public AttorneyServiceImpl(AttorneyRepository repository,
            AttorneyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long save(AttorneyFormDto formDto) {
        Attorney entity = mapper.toAttorneyFromAttorneyFormDto(formDto, new CycleAvoidingMappingContext());
        if(entity.getWorkingStatus().equals(WorkingStatus.INACTIVE)) {
            entity.setInvestigator(null);
        }
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public AttorneyDto findById(Long targetId) {
        Attorney entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        AttorneyDto dto = mapper.toAttorneyDto(entity);
        return dto;
    }
    
    @Override
    @Transactional
    public AttorneyFormDto findFormById(Long targetId) {
        Attorney entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        AttorneyFormDto formDto = mapper.toAttorneyFormDtoFromAttorney(entity);
        return formDto;
    }

    @Override
    @Transactional
    public List<AttorneyDto> findAll() {
        List<AttorneyDto> dtoList = mapper.toAttorneyDtoList(repository.findAll());
        return dtoList;
    }

    @Override
    @Transactional
    public List<AttorneyDto> findAllActive() {
        List<AttorneyDto> activeDtoList = mapper
                .toAttorneyDtoList(repository.findByWorkingStatus(WorkingStatus.ACTIVE));
        return activeDtoList;
    }
    
    @Override
    @Transactional
    public List<AttorneyMinimalDto> findAllActiveMinimalDtos() {
        List<AttorneyMinimalDto> minimalDtoList = mapper
                .toAttorneyMinimalDtoList(repository.findByWorkingStatus(WorkingStatus.ACTIVE));
        return minimalDtoList;
    }

    @Override
    @Transactional
    public boolean delete(AttorneyDto dto) {
        if(dto.getCaseload().isEmpty()) {
            Attorney entity = mapper
                    .toAttorney(dto, new CycleAvoidingMappingContext());
            repository.delete(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        Attorney entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        if(entity.getCaseload().isEmpty()) {
            repository.delete(entity);
            return true;
        } else {
            return false;
        }
    }
    
    

}
