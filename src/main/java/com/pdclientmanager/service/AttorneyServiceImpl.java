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
    public void create(Attorney entity) {
        dao.create(entity);
    }

    @Override
    @Transactional
    public Attorney getById(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional
    public List<Attorney> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public void update(Attorney entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(Attorney entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
    
    @Transactional
    public List<Attorney> getAllActive() {
        return dao.getAllActive();
    }

}
