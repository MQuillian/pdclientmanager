package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.entity.EmploymentStatus;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.repository.EmployeeRepository;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Service
public class InvestigatorServiceImpl implements InvestigatorService {
    
    private EmployeeRepository<Investigator> repository;
    private InvestigatorMapper mapper;
    
    @Autowired
    public InvestigatorServiceImpl(EmployeeRepository<Investigator> repository, InvestigatorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(InvestigatorFormDto dto) {
        Investigator entity = mapper.toInvestigatorFromInvestigatorFormDto(dto, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public InvestigatorDto findById(Long targetId) {
        Investigator entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        InvestigatorDto dto = mapper.toInvestigatorDto(entity);
        return dto;
    }
    
    @Override
    @Transactional
    public InvestigatorFormDto findFormById(Long targetId) {
        Investigator entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        InvestigatorFormDto formDto = mapper.toInvestigatorFormDtoFromInvestigator(entity);
        return formDto;
    }

    @Override
    @Transactional
    public List<InvestigatorDto> findAll() {
        List<InvestigatorDto> dtoList = mapper.toInvestigatorDtoList(repository.findAll());
        return dtoList;
    }
    
    @Override
    @Transactional
    public List<InvestigatorDto> findAllActive() {
        List<InvestigatorDto> dtoList = mapper
                .toInvestigatorDtoList(repository.findByEmploymentStatus(EmploymentStatus.ACTIVE));
        return dtoList;
    }

    @Override
    @Transactional
    public void delete(InvestigatorDto dto) {
        Investigator entity = mapper.toInvestigator(dto, new CycleAvoidingMappingContext());
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }
}
