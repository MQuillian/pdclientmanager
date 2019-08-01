package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
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
    public void persist(Investigator entity) {
        dao.persist(entity);
    }

    @Override
    @Transactional
    public Investigator getById(Long targetId) {
        return dao.getById(targetId);
    }
    
    @Transactional
    public Investigator getByIdWithInitializedAssignedAttorneys(Long targetId) {
        Investigator investigator = dao.getById(targetId);
        Hibernate.initialize(investigator.getAssignedAttorneys());
        return investigator;
    }

    @Override
    @Transactional
    public List<Investigator> getAll() {
        return dao.getAll();
    }
    
    @Override
    @Transactional
    public List<Investigator> getAllActive() {
        return dao.getAllActive();
    }

    @Override
    @Transactional
    public void merge(Investigator entity) {
        dao.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(Investigator entity) {
        dao.delete(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        return delete(getById(targetId));
    }
}
