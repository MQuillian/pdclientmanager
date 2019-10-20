package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.CaseDaoImpl;
import com.pdclientmanager.model.dto.CaseDto;
import com.pdclientmanager.model.entity.Case;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class CaseServiceImpl implements CaseService {

    private CaseDaoImpl dao;
    private CaseMapper caseMapper;
    
    @Autowired
    public CaseServiceImpl(CaseDaoImpl dao, CaseMapper caseMapper) {
        this.dao = dao;
        this.caseMapper = caseMapper;
    }
    
    @Override
    @Transactional
    public void persist(CaseDto dto) {
        
        Case entity = caseMapper.toCase(dto, new CycleAvoidingMappingContext());
        dao.persist(entity);
    }

    @Override
    @Transactional
    public CaseDto getById(Long targetId) {
        
        CaseDto dto = caseMapper.toCaseDto(dao.getById(targetId));
        return dto;
    }

    @Override
    @Transactional
    public List<CaseDto> getAll() {
        
        List<CaseDto> dtoList = caseMapper.toCaseDtoList(dao.getAll());
        return dtoList;
    }

    @Override
    @Transactional
    public void merge(CaseDto dto) {
        
        Case entity = caseMapper.toCase(dto, new CycleAvoidingMappingContext());
        dao.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(CaseDto dto) {
        
        Case entity = caseMapper.toCase(dto, new CycleAvoidingMappingContext());
        dao.delete(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        
        return delete(getById(targetId));
    }
    
    @Override
    @Transactional
    public List<CaseDto> getAllWithInitializedClients() {
        
        List<CaseDto> dtoList = caseMapper.toCaseDtoList(dao.getAllWithInitializedClients());
        return dtoList;
    }
    
    @Override
    @Transactional
    public List<CaseDto> getAllActiveByAttorneyId(Long attorneyId) {
        
        List<CaseDto> dtoList = caseMapper.toCaseDtoList(
                dao.getAllActiveByAttorneyId(attorneyId));
        return dtoList;
        
    }
}
