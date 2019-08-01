package com.pdclientmanager.service;

import java.util.List;

public interface CrudService<T> {
    
    void persist(final T entity);
    
    T getById(final Long targetId);
        
    List<T> getAll();
    
    void merge(final T entity);
    
    boolean delete(final T entity);
    
    boolean deleteById(final Long id);
}
