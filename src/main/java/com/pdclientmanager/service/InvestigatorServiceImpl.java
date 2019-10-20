package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Service
public class InvestigatorServiceImpl implements InvestigatorService {
    private GenericEmployeeDaoImpl<Investigator> dao;
    private InvestigatorMapper mapper;
    
    @Autowired
    public InvestigatorServiceImpl(GenericEmployeeDaoImpl<Investigator> dao, InvestigatorMapper mapper) {
        this.dao = dao;
        this.dao.setClass(Investigator.class);
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long persist(InvestigatorFormDto dto) {
        Investigator entity = mapper.toInvestigatorFromInvestigatorFormDto(dto, new CycleAvoidingMappingContext());
        dao.persist(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public InvestigatorDto getById(Long targetId) {
        InvestigatorDto dto = mapper.toInvestigatorDto(dao.getById(targetId));
        return dto;
    }

    @Override
    @Transactional
    public List<InvestigatorDto> getAll() {
        List<InvestigatorDto> dtoList = mapper.toInvestigatorDtoList(dao.getAll());
        return dtoList;
    }
    
    @Override
    @Transactional
    public List<InvestigatorDto> getAllActive() {
        List<InvestigatorDto> dtoList = mapper.toInvestigatorDtoList(dao.getAllActive());
        return dtoList;
    }

    @Override
    @Transactional
    public Long merge(InvestigatorFormDto dto) {
        Investigator entity = mapper.toInvestigatorFromInvestigatorFormDto(dto, new CycleAvoidingMappingContext());
        dao.merge(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public boolean delete(InvestigatorDto dto) {
        Investigator entity = mapper.toInvestigator(dto, new CycleAvoidingMappingContext());
        dao.delete(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        Investigator entity = dao.getById(targetId);
        dao.delete(entity);
        return true;
    }
}
