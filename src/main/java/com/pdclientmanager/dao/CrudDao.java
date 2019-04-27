package com.pdclientmanager.dao;

import java.util.List;

public interface CrudDao<T> {
    
    void setClass(Class<T> entityClass);
    
    void create(final T newObject);
    
    T getById(final Long targetId);
    
    List<T> getAll();
    
    void update(final T entity);
    
    void delete(final T entity);
    
    void deleteById(final Long id);
}
