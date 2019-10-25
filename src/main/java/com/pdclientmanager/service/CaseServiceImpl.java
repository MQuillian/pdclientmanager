package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.dto.CaseFormDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.model.entity.CaseStatus;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class CaseServiceImpl implements CaseService {
    
    private CaseRepository repository;
    private CaseMapper mapper;
    
    @Autowired
    public CaseServiceImpl(CaseRepository repository, CaseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(CaseFormDto formDto) {
        Case entity = mapper.toCaseFromCaseFormDto(formDto, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public CaseDto findById(Long targetId) {
        Case entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        CaseDto dto = mapper.toCaseDto(entity);
        return dto;
    }

    @Override
    @Transactional
    public CaseFormDto findFormById(Long targetId) {
        Case entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        CaseFormDto formDto = mapper.toCaseFormDtoFromCase(entity);
        return formDto;
    }

    @Override
    @Transactional
    public List<CaseDto> findAll() {
        List<CaseDto> dtoList = mapper.toCaseDtoList(repository.findAll());
        return dtoList;
    }

    @Override
    @Transactional
    public List<CaseDto> findAllOpen() {
        List<CaseDto> openDtoList = mapper.toCaseDtoList(
                repository.findByCaseStatus(CaseStatus.OPEN));
        return openDtoList;
    }
    
    @Override
    @Transactional
    public List<CaseDto> findAllOpenWithAttorneyId(Long targetId) {
        List<CaseDto> openDtoList = mapper.toCaseDtoList(
                repository.findByCaseStatusAndAttorney_Id(CaseStatus.OPEN, targetId));
        return openDtoList;
    }

    @Override
    @Transactional
    public void delete(CaseDto dto) {
        Case entity = mapper.toCase(dto, new CycleAvoidingMappingContext());
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
