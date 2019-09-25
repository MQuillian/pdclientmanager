package com.pdclientmanager.service;

import java.util.List;

public interface CrudService<T, S> {
    
    void persist(final T dto);
    
    T getById(final Long targetId);
        
    List<T> getAll();
    
    void merge(final T dto);
    
    boolean delete(final T dto);
    
    boolean deleteById(final Long id);
}
