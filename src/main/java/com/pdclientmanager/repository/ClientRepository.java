package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Client;

public interface ClientRepository extends BaseRepository<Client> {

    List<Client> findFirst10ByNameContaining(String query);
    
}
