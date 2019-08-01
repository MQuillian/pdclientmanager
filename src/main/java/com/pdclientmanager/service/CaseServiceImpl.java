package com.pdclientmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.dao.CaseDaoImpl;
import com.pdclientmanager.model.Case;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseDaoImpl dao;
    
    @Override
    @Transactional
    public void persist(Case entity) {
        dao.persist(entity);
    }

    @Override
    @Transactional
    public Case getById(Long targetId) {
        return dao.getById(targetId);
    }

    @Override
    @Transactional
    public List<Case> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public void merge(Case entity) {
        dao.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(Case entity) {
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
    public List<Case> getAllWithInitializedClients() {
        return dao.getAllWithInitializedClients();
    }
    
    @Override
    @Transactional
    public List<Case> getAllActiveByAttorneyIdWithInitializedClient(Long attorneyId) {
        return dao.getAllActiveByAttorneyIdWithInitializedClient(attorneyId);
    }
}
