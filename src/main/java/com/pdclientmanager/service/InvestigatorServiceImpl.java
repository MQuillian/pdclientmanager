package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.Investigator;

@Service
public class InvestigatorServiceImpl implements EmployeeService<Investigator> {
    
    GenericEmployeeDaoImpl<Investigator> dao;
    
    @Autowired
    public void setDao(GenericEmployeeDaoImpl<Investigator> typedDao) {
        dao = typedDao;
        dao.setClass(Investigator.class);
    }

    @Override
    @Transactional
    public void create(Investigator entity) {
        dao.create(entity);
    }

    @Override
    @Transactional
    public Investigator getById(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional
    public List<Investigator> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public void update(Investigator entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(Investigator entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
    
    @Transactional
    public List<Investigator> getAllActive() {
        return dao.getAllActive();
    }
    
}
