package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.dto.AttorneyDto;
import com.pdclientmanager.model.dto.AttorneyFormDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class AttorneyServiceImpl implements AttorneyService {

    private GenericEmployeeDaoImpl<Attorney> dao;
    private AttorneyMapper mapper;
    
    @Autowired
    public AttorneyServiceImpl(GenericEmployeeDaoImpl<Attorney> dao,
            AttorneyMapper mapper) {
        this.dao = dao;
        this.dao.setClass(Attorney.class);
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long persist(AttorneyFormDto formDto) {
        
        Attorney entity = mapper.toAttorneyFromAttorneyFormDto(formDto, new CycleAvoidingMappingContext());
        dao.persist(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public AttorneyDto getById(Long targetId) {
        AttorneyDto dto = mapper.toAttorneyDto(dao.getById(targetId));
        return dto;
    }
    
    @Override
    @Transactional
    public AttorneyFormDto getFormById(Long targetId) {
        AttorneyFormDto formDto = mapper.toAttorneyFormDtoFromAttorney(dao.getById(targetId));
        return formDto;
    }

    @Override
    @Transactional
    public List<AttorneyDto> getAll() {
        List<AttorneyDto> dtoList = mapper.toAttorneyDtoList(dao.getAll());
        return dtoList;
    }

    @Override
    @Transactional
    public List<AttorneyDto> getAllActive() {
        List<AttorneyDto> activeDtoList = mapper.toAttorneyDtoList(dao.getAllActive());
        return activeDtoList;
    }

    @Override
    @Transactional
    public Long merge(AttorneyFormDto formDto) {
        Attorney entity = mapper.toAttorneyFromAttorneyFormDto(formDto, new CycleAvoidingMappingContext());
        dao.merge(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public boolean delete(AttorneyDto dto) {
        if(dto.getCaseload().isEmpty()) {
            Attorney entity = mapper.toAttorney(dto, new CycleAvoidingMappingContext());
            dao.delete(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Attorney entity = dao.getById(id);
        if(entity.getCaseload().isEmpty()) {
            dao.delete(entity);
            return true;
        } else {
            return false;
        }
    }
    
    

}
