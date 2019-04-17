package com.pdclientmanager.dao;

import java.util.List;

public interface CrudDao<T> {
    
    void save(T newObject);
    
    List<T> search(String searchTerm);
    
    List<T> list();
    
    void update(Long targetId, T updatedObject);
    
    void delete(Long targetId);
    
    T getById(Long targetId);

}
