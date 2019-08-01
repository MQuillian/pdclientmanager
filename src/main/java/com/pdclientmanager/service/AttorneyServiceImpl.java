package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.Attorney;

@Service
public class AttorneyServiceImpl implements EmployeeService<Attorney> {

    GenericEmployeeDaoImpl<Attorney> dao;
    
    @Autowired
    public void setDao(GenericEmployeeDaoImpl<Attorney> typedDao) {
        dao = typedDao;
        dao.setClass(Attorney.class);
    }

    @Override
    @Transactional
    public void persist(Attorney entity) {
        dao.persist(entity);
    }

    @Override
    @Transactional
    public Attorney getById(Long targetId) {
        return dao.getById(targetId);
    }
    
    @Override
    @Transactional
    public Attorney getByIdWithInitializedAssignedAttorneys(Long targetId) {
        // No lazy initialized methods in Attorney class - method is not used
        return dao.getById(targetId);
    }

    @Override
    @Transactional
    public List<Attorney> getAll() {
        return dao.getAll();
    }
    
    @Override
    @Transactional
    public List<Attorney> getAllActive() {
        return dao.getAllActive();
    }

    @Override
    @Transactional
    public void merge(Attorney entity) {
        dao.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(Attorney entity) {
        if(entity.getCaseload().isEmpty()) {
            dao.delete(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        return delete(getById(targetId));
    }
}
