package com.pdclientmanager.service;

import java.util.List;

import com.pdclientmanager.model.form.ClientForm;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.model.projection.ClientProjection;

public interface ClientService {

    Long save(final ClientForm form);
    
    <T> T findById(final Long targetId, Class<T> type);
    
    ClientForm findFormById(final Long targetId);
    
    List<ClientLightProjection> findByName(String query);
        
    <T> List<T> findAllBy(Class<T> type);
    
    void delete(final ClientProjection form);
    
    void deleteById(final Long targetId);
}
