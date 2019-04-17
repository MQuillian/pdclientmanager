package com.pdclientmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pdclientmanager.dao.AttorneyDaoImpl;
import com.pdclientmanager.model.Attorney;

@Service
public class AttorneyServiceImpl implements CrudService<Attorney> {
    
    @Autowired
    AttorneyDaoImpl attorneyDao;

    @Override
    @Transactional
    public void save(Attorney newAttorney) {
        attorneyDao.save(newAttorney);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attorney> search(String searchTerm) {
        return attorneyDao.search(searchTerm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attorney> list() {
        return attorneyDao.list();
    }

    @Override
    @Transactional
    public void update(Long targetId, Attorney updatedAttorney) {
        attorneyDao.update(targetId, updatedAttorney);
    }

    @Override
    @Transactional
    public void delete(Long targetId) {
        attorneyDao.delete(targetId);
    }
    
    @Override
    @Transactional
    public Attorney getById(Long targetId) {
        return attorneyDao.getById(targetId);
    }
}
