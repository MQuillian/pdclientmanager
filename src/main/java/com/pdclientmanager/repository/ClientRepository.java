package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.repository.entity.Client;

public interface ClientRepository extends BaseRepository<Client> {

    @EntityGraph(value = "Client.fullProjection")
    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    <T> List<T> findAllBy(Class<T> type);
    
    List<ClientLightProjection> findFirst10ByNameContaining(String query);
}
