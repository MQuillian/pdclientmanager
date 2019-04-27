package com.pdclientmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pdclientmanager.dao.CrudDao;
import com.pdclientmanager.model.Attorney;

@Service
public class AttorneyServiceImpl implements CrudService<Attorney> {
    
    CrudDao<Attorney> attorneyDao;
    
    @Autowired
    public void setDao(CrudDao<Attorney> dao) {
        attorneyDao = dao;
        attorneyDao.setClass(Attorney.class);
    }

    @Override
    @Transactional
    public void create(final Attorney newAttorney) {
        attorneyDao.create(newAttorney);
    }

    @Override
    @Transactional(readOnly = true)
    public Attorney getById(final Long targetId) {
        return attorneyDao.getById(targetId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attorney> getAll() {
        return attorneyDao.getAll();
    }

    @Override
    @Transactional
    public void update(final Attorney updatedAttorney) {
        attorneyDao.update(updatedAttorney);
    }

    @Override
    @Transactional
    public void delete(final Attorney attorneyToDelete) {
        attorneyDao.delete(attorneyToDelete);
    }
    
    @Override
    @Transactional
    public void deleteById(final Long id) {
        attorneyDao.deleteById(id);
    }
}
