package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.dao.CrudDao;

public interface CrudService<T> {
    
    public void setDao(CrudDao<T> dao);
    
    void create(final T entity);
    
    T getById(final Long id);
    
    List<T> getAll();
    
    void update(final T entity);
    
    void delete(final T entity);
    
    void deleteById(final Long id);
}
